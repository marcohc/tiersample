package com.marcohc.tier.sample.feature.map.adapter

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.marcohc.tier.sample.shared.data.models.Vehicle

data class VehicleItem(
    val vehicle: Vehicle
) : ClusterItem {

    override fun getPosition() = LatLng(vehicle.latitude, vehicle.longitude)

    override fun getTitle() = ""

    override fun getSnippet() = ""
}
