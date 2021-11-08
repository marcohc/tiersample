package com.marcohc.tier.sample.shared.data.models

import org.threeten.bp.LocalDateTime

data class Vehicle(
    val id: String? = "",
    val vehicleId: String? = "",
    val hardwareId: String? = "",
    val zoneId: String = "",
    val resolvedBy: String? = "",
    val resolvedAt: LocalDateTime? = null,
    val resolution: String? = "",
    val battery: Int = 0,
    val state: String = "",
    val model: String = "",
    val fleetbirdId: Int? = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
