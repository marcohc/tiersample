package com.marcohc.tier.sample.feature.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import com.marcohc.terminator.core.mvi.ui.MviActivity
import com.marcohc.terminator.core.mvi.ui.MviConfig
import com.marcohc.terminator.core.mvi.ui.MviConfigType
import com.marcohc.terminator.core.utils.setVisibleEitherGone
import com.marcohc.terminator.core.utils.showSnackbar
import com.marcohc.terminator.core.utils.unsafeLazy
import com.marcohc.tier.sample.feature.map.adapter.MarkerClusterRenderer
import com.marcohc.tier.sample.feature.map.adapter.VehicleItem
import com.marcohc.tier.sample.feature.map.databinding.MapActivityBinding

class MapActivity : MviActivity<MapIntention, MapState>() {

    override val mviConfig = MviConfig(
        scopeId = MapModule.scopeId,
        layoutId = R.layout.map_activity,
        mviConfigType = MviConfigType.SCOPE_AND_NAVIGATION
    )

    private val viewBinding: MapActivityBinding by unsafeLazy { MapActivityBinding.bind(inflatedView) }
    private lateinit var googleMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<VehicleItem>

    override fun afterComponentCreated(savedInstanceState: Bundle?) {

        // Set up map and when ready send initial intention
        (supportFragmentManager.findFragmentById(R.id.supportMapFragment) as SupportMapFragment).getMapAsync { map ->
            googleMap = map
            clusterManager = ClusterManager(this, map)
            clusterManager.renderer = MarkerClusterRenderer(this, googleMap, clusterManager)
            clusterManager.setOnClusterItemClickListener {
                sendIntention(MapIntention.ItemClick(it))
                false
            }
            googleMap.setOnCameraIdleListener(clusterManager)
            googleMap.uiSettings.isMapToolbarEnabled = false
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            sendIntention(MapIntention.Initial)
        }

        viewBinding.errorCard.sendIntentionOnClick { MapIntention.ErrorClick }
    }

    override fun render(state: MapState) {
        with(state) {

            viewBinding.loadingLayout.setVisibleEitherGone(loading)

            viewBinding.errorCard.setVisibleEitherGone(error)
            showErrorMessageExecutable.execute { this@MapActivity.showSnackbar(R.string.widget_general_error) }

            if (items.isNotEmpty()) {
                clusterManager.addItems(items)
                clusterManager.cluster()
            }

            moveToCurrentLocation.consume()?.let { latLng ->
                if (ActivityCompat.checkSelfPermission(this@MapActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googleMap.isMyLocationEnabled = true
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM))
            }
        }
    }

    private companion object {
        const val ZOOM = 12f
    }
}
