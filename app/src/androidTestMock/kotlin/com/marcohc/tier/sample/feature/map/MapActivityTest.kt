package com.marcohc.tier.sample.feature.map

import com.marcohc.terminator.core.mvi.android.test.MviActivityTest
import com.marcohc.terminator.core.mvi.ui.consumable.OneTimeExecutable
import com.marcohc.tier.sample.feature.map.adapter.VehicleItem
import com.marcohc.tier.sample.shared.data.models.Vehicle
import org.junit.Test

internal class MapActivityTest : MviActivityTest<MapIntention, MapState, MapRobot>(
    MapActivity::class.java,
    MapModule.scopeId,
    MapRobot()
) {

    @Test
    fun whenScreenStartsThenInitialIntentionIsSent() {
        MapIntention.Initial.assertFirstIntention()
    }

    @Test
    fun givenErrorWhenErrorClickThenErrorClickIntentionIsSent() {
        setState(MapState(error = true))

        robot {
            clickOnError()
        }

        MapIntention.ErrorClick.assertIntentionAt(1)
    }

    @Test
    fun givenItemsWhenItemClickThenItemClickIntentionIsSent() {
        val id = "myId"
        val item = VehicleItem(Vehicle(id = id))
        setState(MapState(items = listOf(item)))

        robot {
            clickOnMarker(id)
        }

        MapIntention.ItemClick(item).assertIntentionAt(1)
    }

    @Test
    fun givenLoadingThenLoadingLayoutIsDisplayed() {
        setState(MapState(loading = true))

        robot {
            checkLoadingDisplayed()
        }
    }

    @Test
    fun givenLoadingThenLoadingLayoutIsNotDisplayed() {
        setState(MapState(loading = false))

        robot {
            checkLoadingNotDisplayed()
        }
    }

    @Test
    fun givenErrorThenErrorIsDisplayed() {
        setState(MapState(error = true))

        robot {
            checkErrorDisplayed()
        }
    }

    @Test
    fun givenErrorThenErrorIsNotDisplayed() {
        setState(MapState(error = false))

        robot {
            checkErrorNotDisplayed()
        }
    }

    @Test
    fun whenErrorThenErrorSnackBarIsShown() {
        setState(MapState(showErrorMessageExecutable = OneTimeExecutable.load()))

        robot {
            checkErrorToastDisplayed()
        }
    }
}
