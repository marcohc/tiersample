package com.marcohc.tier.sample.feature.detail

import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.marcohc.terminator.core.mvi.domain.MviBaseInteractor
import com.marcohc.terminator.core.utils.exhaustive
import com.marcohc.tier.sample.feature.detail.DetailInteractor.DetailAction
import io.reactivex.Observable

internal class DetailInteractor(
    private val getSelectedVehicleUseCase: GetSelectedVehicleUseCase,
    private val analytics: DetailAnalytics,
) : MviBaseInteractor<DetailIntention, DetailAction, DetailState>(DetailState()) {

    override fun intentionToAction(): (DetailIntention) -> Observable<out DetailAction> = { intention ->
        when (intention) {
            is DetailIntention.Initial -> initial().toObservable()
        }
    }

    private fun initial() = analytics.trackScreen()
        .andThen(getSelectedVehicleUseCase.execute())
        .map { optional ->
            when (optional) {
                is Some -> {
                    with(optional.value) {
                        DetailAction.Render(
                            model = model,
                            zone = zoneId,
                            batteryFormatted = battery.toString(),
                            state = state
                        )
                    }
                }
                is None -> throw IllegalStateException("For the shake of simplicity in this sample app...")
            }
        }
        .onErrorReturn {
            throw IllegalStateException("...I choose to crash but the error could also be handled in the UI in a nicer way")
        }

    override fun actionToState(): (DetailState, DetailAction) -> DetailState = { state, action ->
        with(state) {
            when (action) {
                is DetailAction.Render -> copy(
                    model = action.model,
                    zone = action.zone,
                    batteryFormatted = action.batteryFormatted,
                    state = action.state
                )
            }.exhaustive
        }
    }

    internal sealed class DetailAction {
        data class Render(
            val model: String = "",
            val zone: String = "",
            val batteryFormatted: String = "",
            val state: String = "",
        ) : DetailAction()
    }
}
