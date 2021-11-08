package com.marcohc.tier.sample.feature.map

import com.marcohc.tier.sample.shared.data.models.Vehicle
import com.marcohc.tier.sample.shared.data.repositories.VehicleRepository

internal class SaveSelectedVehicleUseCase(
    private val repository: VehicleRepository
) {

    fun execute(vehicle: Vehicle) = repository.saveSelectedToLocal(vehicle)
}
