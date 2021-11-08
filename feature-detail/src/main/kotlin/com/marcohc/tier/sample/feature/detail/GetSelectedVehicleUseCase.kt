package com.marcohc.tier.sample.feature.detail

import com.marcohc.tier.sample.shared.data.repositories.VehicleRepository

internal class GetSelectedVehicleUseCase(
    private val repository: VehicleRepository
) {

    fun execute() = repository.getSelectedFromLocal()
}
