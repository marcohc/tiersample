package com.marcohc.tier.sample.feature.detail

import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.marcohc.terminator.core.mvi.android.test.Robot

/**
 * Robots can be reused across different instrumentation tests
 */
internal class DetailRobot : Robot {

    fun checkModel(value: String) {
        assertDisplayed(value)
    }

    fun checkZone(value: String) {
        assertDisplayed(value)
    }

    fun checkStatus(value: String) {
        assertDisplayed(value)
    }

    fun checkBattery(value: String) {
        assertDisplayed(value)
    }
}
