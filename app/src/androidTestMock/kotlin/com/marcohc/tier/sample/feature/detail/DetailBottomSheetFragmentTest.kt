package com.marcohc.tier.sample.feature.detail

import com.marcohc.terminator.core.mvi.android.test.MviBottomSheetDialogFragmentTest
import org.junit.Test

internal class DetailBottomSheetFragmentTest : MviBottomSheetDialogFragmentTest<DetailIntention, DetailState, DetailRobot>(
    DetailBottomSheetFragment.newInstance(),
    DetailModule.scopeId,
    DetailRobot()
) {

    @Test
    fun whenScreenStartsThenInitialIntentionIsSent() {
        DetailIntention.Initial.assertFirstIntention()
    }

    @Test
    fun givenErrorWhenErrorClickThenErrorClickIntentionIsSent() {
        setState(
            DetailState(
                model = "model",
                zone = "zoneId",
                batteryFormatted = "100",
                state = "state"
            )
        )

        robot {
            checkModel("model")
            checkZone("zoneId")
            checkStatus("state")
            checkBattery("100")
        }
    }
}
