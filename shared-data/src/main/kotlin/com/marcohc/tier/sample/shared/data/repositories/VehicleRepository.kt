package com.marcohc.tier.sample.shared.data.repositories

import com.gojuno.koptional.Optional
import com.marcohc.tier.sample.shared.data.api.VehicleApi
import com.marcohc.tier.sample.shared.data.models.Vehicle
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

class VehicleRepository(
    private val api: VehicleApi,
    private val scheduler: Scheduler
) {

    private var selectedVehicle: Vehicle? = null

    fun saveSelectedToLocal(vehicle: Vehicle): Completable = Completable.fromAction { selectedVehicle = vehicle }

    fun getSelectedFromLocal(): Single<Optional<Vehicle>> = Single.fromCallable { Optional.toOptional(selectedVehicle) }

    fun getFromNetwork(
        latitude: Double? = null,
        longitude: Double? = null
    ): Single<List<Vehicle>> = api
        .getVehicles(
            longitude = latitude,
            latitude = longitude
        )
        .map { it.data.current }
        .subscribeOn(scheduler)
}
