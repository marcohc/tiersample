package com.marcohc.tier.sample.feature.map

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.marcohc.terminator.core.mvi.android.test.Robot

/**
 * Robots can be reused across different instrumentation tests
 */
internal class MapRobot : Robot {

    fun clickOnError() {
        clickOn(R.id.errorCard)
    }

    fun clickOnMarker(id: String) {
        val device = UiDevice.getInstance(getInstrumentation())
        val marker = device.findObject(UiSelector().descriptionContains(id))
        marker.click()
    }

    fun checkLoadingDisplayed() {
        assertDisplayed(R.id.loadingLayout)
    }

    fun checkLoadingNotDisplayed() {
        assertNotDisplayed(R.id.loadingLayout)
    }

    fun checkErrorDisplayed() {
        assertDisplayed(R.id.errorCard)
    }

    fun checkErrorToastDisplayed() {
        assertDisplayed(R.string.widget_general_error)
    }

    fun checkErrorNotDisplayed() {
        assertNotDisplayed(R.id.errorCard)
    }
}
