package com.marcohc.tier.sample.feature.detail

sealed class DetailIntention {
    object Initial : DetailIntention()
}

data class DetailState(
    val model: String = "",
    val zone: String = "",
    val batteryFormatted: String = "",
    val state: String = "",
)
