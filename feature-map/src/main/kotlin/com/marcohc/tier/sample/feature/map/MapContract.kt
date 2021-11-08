package com.marcohc.tier.sample.feature.map

import com.google.android.gms.maps.model.LatLng
import com.marcohc.terminator.core.mvi.ui.consumable.Consumable
import com.marcohc.terminator.core.mvi.ui.consumable.OneTimeExecutable
import com.marcohc.tier.sample.feature.map.adapter.VehicleItem

sealed class MapIntention {
    object Initial : MapIntention()
    object ErrorClick : MapIntention()
    data class ItemClick(val item: VehicleItem) : MapIntention()
}

data class MapState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val showErrorMessageExecutable: OneTimeExecutable = OneTimeExecutable.empty(),
    val moveToCurrentLocation: Consumable<LatLng> = Consumable(),
    val items: List<VehicleItem> = emptyList()
)
