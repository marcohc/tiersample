package com.marcohc.tier.sample.shared.data.repositories

import com.gojuno.koptional.None
import com.marcohc.terminator.core.mvi.test.mockError
import com.marcohc.terminator.core.mvi.test.mockValue
import com.marcohc.tier.sample.shared.data.api.DataResponse
import com.marcohc.tier.sample.shared.data.api.GetVehicleResponse
import com.marcohc.tier.sample.shared.data.api.VehicleApi
import com.marcohc.tier.sample.shared.data.models.Vehicle
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class VehicleRepositoryTest {

    @Mock
    private lateinit var api: VehicleApi

    private val scheduler = Schedulers.trampoline()

    private lateinit var repository: VehicleRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = VehicleRepository(
            api = api,
            scheduler = scheduler
        )
    }

    @Test
    fun `given error when getFromNetwork then propagate error`() {
        val exception = IllegalStateException()
        api.getVehicles().mockError(exception)

        repository.getFromNetwork()
            .test()
            .assertError(exception)
    }

    @Test
    fun `given no error when getFromNetwork then return data`() {
        val response = GetVehicleResponse(data = DataResponse(current = listOf(mock())))
        api.getVehicles().mockValue(response)

        repository.getFromNetwork()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { models -> models.size == 1 }
    }

    @Test
    fun `given no stored value when getSelectedFromLocal then return None`() {
        repository.getSelectedFromLocal().test().assertValue { it is None }
    }

    @Test
    fun `given stored value when getSelectedFromLocal then return None`() {
        val vehicle = mock<Vehicle>()
        repository.saveSelectedToLocal(vehicle).test()

        repository.getSelectedFromLocal().test().assertValue { it.toNullable() == vehicle }
    }
}
