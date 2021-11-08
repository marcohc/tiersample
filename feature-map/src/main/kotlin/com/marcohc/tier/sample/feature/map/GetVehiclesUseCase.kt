package com.marcohc.tier.sample.feature.map

import com.marcohc.tier.sample.feature.map.adapter.VehicleItem
import com.marcohc.tier.sample.shared.data.repositories.VehicleRepository
import io.reactivex.Single

internal class GetVehiclesUseCase(
    private val repository: VehicleRepository
) {

    fun execute(
        latitude: Double? = null,
        longitude: Double? = null
    ): Single<List<VehicleItem>> = repository.getFromNetwork(latitude, longitude)
        .map { vehicles -> vehicles.map { vehicle -> VehicleItem(vehicle) } }
}
