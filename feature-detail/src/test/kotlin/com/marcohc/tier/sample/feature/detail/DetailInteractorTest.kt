package com.marcohc.tier.sample.feature.detail

import com.gojuno.koptional.Some
import com.marcohc.terminator.core.mvi.domain.MviBaseInteractor
import com.marcohc.terminator.core.mvi.test.MviInteractorTest
import com.marcohc.terminator.core.mvi.test.mockComplete
import com.marcohc.terminator.core.mvi.test.mockNever
import com.marcohc.terminator.core.mvi.test.mockValue
import com.marcohc.tier.sample.feature.detail.DetailInteractor.DetailAction
import com.marcohc.tier.sample.shared.data.models.Vehicle
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.mockito.Mock

internal class DetailInteractorTest : MviInteractorTest<DetailIntention, DetailAction, DetailState>() {

    @Mock
    lateinit var getSelectedVehicleUseCase: GetSelectedVehicleUseCase

    @Mock
    lateinit var analytics: DetailAnalytics

    override fun provideInteractor(): MviBaseInteractor<DetailIntention, DetailAction, DetailState> = DetailInteractor(
        getSelectedVehicleUseCase = getSelectedVehicleUseCase,
        analytics = analytics
    )

    @Test
    fun `when initial then track event`() {
        getSelectedVehicleUseCase.execute().mockNever()

        sendIntention(DetailIntention.Initial)

        verify(analytics).trackScreen()
    }

    @Test
    fun `given selected vehicle when initial then render`() {
        val vehicle = Vehicle(
            model = "model",
            zoneId = "zoneId",
            state = "state",
            battery = 100
        )
        analytics.trackScreen().mockComplete()
        getSelectedVehicleUseCase.execute().mockValue(Some(vehicle))

        sendIntention(DetailIntention.Initial)

        assertTypedStateAt<DetailState>(1) { model == vehicle.model && zone == vehicle.zoneId && state == vehicle.state && batteryFormatted == vehicle.battery.toString() }
    }

    // Tests for error states to be implemented below this line
}
