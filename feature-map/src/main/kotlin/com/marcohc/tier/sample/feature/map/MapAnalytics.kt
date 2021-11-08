package com.marcohc.tier.sample.feature.map

import io.reactivex.Completable

internal class MapAnalytics {

    fun trackScreen() = Completable.fromAction {
        // Track your analytics events here
    }

    fun trackItemClick() = Completable.fromAction {
        // Track your analytics events here
    }

    fun trackErrorClick() = Completable.fromAction {
        // Track your analytics events here
    }
}
