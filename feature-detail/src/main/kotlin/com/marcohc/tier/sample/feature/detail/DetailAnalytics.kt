package com.marcohc.tier.sample.feature.detail

import io.reactivex.Completable

internal class DetailAnalytics {

    fun trackScreen() = Completable.fromAction {
        // Track your analytics events here
    }
}
