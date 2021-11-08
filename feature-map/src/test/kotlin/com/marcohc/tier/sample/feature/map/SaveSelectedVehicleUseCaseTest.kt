package com.marcohc.tier.sample.feature.map

import com.marcohc.terminator.core.mvi.test.mockComplete
import com.marcohc.terminator.core.mvi.test.mockError
import com.marcohc.tier.sample.shared.data.repositories.VehicleRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class SaveSelectedVehicleUseCaseTest {

    @Mock
    private lateinit var repository: VehicleRepository

    private lateinit var useCase: SaveSelectedVehicleUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = SaveSelectedVehicleUseCase(repository)
    }

    @Test
    fun `given error when executed then propagate error`() {
        val exception = IllegalStateException()
        repository.saveSelectedToLocal(any()).mockError(exception)

        useCase.execute(mock())
            .test()
            .assertError(exception)
    }

    @Test
    fun `given network vehicles when executed then return them`() {
        repository.saveSelectedToLocal(any()).mockComplete()

        useCase.execute(mock())
            .test()
            .assertNoErrors()
            .assertComplete()
    }
}
