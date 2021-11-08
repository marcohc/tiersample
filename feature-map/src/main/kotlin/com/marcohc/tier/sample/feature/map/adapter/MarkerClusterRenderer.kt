package com.marcohc.tier.sample.feature.map.adapter

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.marcohc.tier.sample.feature.map.R

class MarkerClusterRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<VehicleItem>
) : DefaultClusterRenderer<VehicleItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: VehicleItem, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.im_electric_bike))
        markerOptions.title(item.vehicle.id)
    }
}
