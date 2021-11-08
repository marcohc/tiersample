package com.marcohc.tier.sample.feature.map

import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.google.android.gms.maps.model.LatLng
import com.marcohc.terminator.core.mvi.domain.MviBaseInteractor
import com.marcohc.terminator.core.mvi.ui.consumable.Consumable
import com.marcohc.terminator.core.mvi.ui.consumable.OneTimeExecutable
import com.marcohc.tier.sample.feature.map.MapInteractor.MapAction
import com.marcohc.tier.sample.feature.map.adapter.VehicleItem
import io.reactivex.Observable

internal class MapInteractor(
    private val saveSelectedVehicleUseCase: SaveSelectedVehicleUseCase,
    private val getVehiclesUseCase: GetVehiclesUseCase,
    private val router: MapRouter,
    private val analytics: MapAnalytics
) : MviBaseInteractor<MapIntention, MapAction, MapState>(defaultState = MapState()) {

    override fun intentionToAction(): (MapIntention) -> Observable<out MapAction> = { intention ->
        when (intention) {
            is MapIntention.Initial -> initial()
            is MapIntention.ErrorClick -> errorClick()
            is MapIntention.ItemClick -> itemClick(intention.item).toObservable()
        }
    }

    private fun initial() = analytics.trackScreen()
        .andThen(router.requestLocationPermission())
        .andThen(router.requestLastLocation())
        .flatMapObservable { lastLocationOptional ->
            when (lastLocationOptional) {
                is Some -> Observable.concat(
                    Observable.just(
                        MapAction.MoveCamera(
                            LatLng(
                                lastLocationOptional.value.latitude,
                                lastLocationOptional.value.longitude
                            )
                        )
                    ),
                    loadVehicles(
                        lastLocationOptional.value.latitude,
                        lastLocationOptional.value.longitude
                    )
                )
                is None -> loadVehicles()
            }
        }
        .onErrorReturn { MapAction.Error }

    private fun itemClick(item: VehicleItem) = analytics.trackItemClick()
        .andThen(saveSelectedVehicleUseCase.execute(item.vehicle))
        .andThen(router.showDetails())

    private fun errorClick() = analytics.trackErrorClick()
        .andThen(loadVehicles())
        .onErrorReturn { MapAction.Error }

    private fun loadVehicles(
        latitude: Double? = null,
        longitude: Double? = null
    ) = getVehiclesUseCase
        .execute(
            latitude,
            longitude
        )
        .toObservable()
        .map<MapAction> { MapAction.Render(it) }
        .startWith(MapAction.Loading)

    override fun actionToState(): (MapState, MapAction) -> MapState = { state, action ->
        with(state) {
            when (action) {
                is MapAction.Loading -> copy(
                    loading = true,
                    error = false
                )
                is MapAction.Error -> copy(
                    loading = false,
                    error = true,
                    showErrorMessageExecutable = OneTimeExecutable.load()
                )
                is MapAction.MoveCamera -> copy(moveToCurrentLocation = Consumable(action.latLng))
                is MapAction.Render -> copy(
                    loading = false,
                    items = action.items
                )
            }
        }
    }

    internal sealed class MapAction {
        object Loading : MapAction()
        object Error : MapAction()
        data class MoveCamera(val latLng: LatLng) : MapAction()
        data class Render(val items: List<VehicleItem>) : MapAction()
    }
}
