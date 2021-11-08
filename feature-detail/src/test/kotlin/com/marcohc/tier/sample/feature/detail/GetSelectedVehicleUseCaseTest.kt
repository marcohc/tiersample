package com.marcohc.tier.sample.feature.detail

import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.marcohc.terminator.core.mvi.test.mockError
import com.marcohc.terminator.core.mvi.test.mockValue
import com.marcohc.tier.sample.shared.data.models.Vehicle
import com.marcohc.tier.sample.shared.data.repositories.VehicleRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class GetSelectedVehicleUseCaseTest {

    @Mock
    private lateinit var repository: VehicleRepository

    private lateinit var useCase: GetSelectedVehicleUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetSelectedVehicleUseCase(repository)
    }

    @Test
    fun `given error when executed then propagate error`() {
        val exception = IllegalStateException()
        repository.getSelectedFromLocal().mockError(exception)

        useCase.execute()
            .test()
            .assertError(exception)
    }

    @Test
    fun `given no local selected vehicle when executed then return them`() {
        repository.getSelectedFromLocal().mockValue(None)

        useCase.execute()
            .test()
            .assertNoErrors()
            .assertValue { it.toNullable() == null }
    }

    @Test
    fun `given local selected vehicle when executed then return them`() {
        val vehicle = mock<Vehicle>()
        repository.getSelectedFromLocal().mockValue(Some(vehicle))

        useCase.execute()
            .test()
            .assertNoErrors()
            .assertValue { it.toNullable() == vehicle }
    }
}
