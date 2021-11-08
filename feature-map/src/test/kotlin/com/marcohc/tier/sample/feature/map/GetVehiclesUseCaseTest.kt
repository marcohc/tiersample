package com.marcohc.tier.sample.feature.map

import com.marcohc.terminator.core.mvi.test.mockError
import com.marcohc.terminator.core.mvi.test.mockValue
import com.marcohc.tier.sample.shared.data.models.Vehicle
import com.marcohc.tier.sample.shared.data.repositories.VehicleRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class GetVehiclesUseCaseTest {

    @Mock
    private lateinit var repository: VehicleRepository

    private lateinit var useCase: GetVehiclesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetVehiclesUseCase(repository)
    }

    @Test
    fun `given error when executed then propagate error`() {
        val exception = IllegalStateException()
        repository.getFromNetwork().mockError(exception)

        useCase.execute()
            .test()
            .assertError(exception)
    }

    @Test
    fun `given network vehicles when executed then return them`() {
        val vehicles = listOf<Vehicle>(mock())
        repository.getFromNetwork().mockValue(vehicles)

        useCase.execute()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { it.size == vehicles.size }
    }
}
