package com.marcohc.tier.sample.feature.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gun0912.tedpermission.rx2.TedPermission
import com.marcohc.terminator.core.mvi.ui.navigation.ActivityNavigationExecutor
import io.reactivex.Completable

internal class MapRouter(
    private val navigator: MapNavigator,
    private val navigationExecutor: ActivityNavigationExecutor
) {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    fun requestLocationPermission(): Completable = TedPermission.create()
        .setRationaleTitle(R.string.rationale_title)
        .setRationaleMessage(R.string.rationale_message)
        .setRationaleConfirmText(R.string.widget_ok)
        .setGotoSettingButtonText(R.string.permission_manager_settings)
        .setGotoSettingButton(true)
        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
        .request()
        .ignoreElement()

    fun requestLastLocation() = navigationExecutor.executeSingle<Optional<Location>> { (activity, emitter) ->

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (fusedLocationClient == null) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            }

            requireNotNull(fusedLocationClient).run {
                lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        emitter.onSuccess(Some(task.result))
                    } else {
                        emitter.onSuccess(None)
                    }
                }
            }
        } else {
            emitter.onSuccess(None)
        }
    }

    fun showDetails() = navigationExecutor.executeCompletable(navigator::goToDetails)
}

interface MapNavigator {
    fun goToDetails(activity: AppCompatActivity)
}
