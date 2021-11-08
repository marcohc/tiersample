package com.marcohc.tier.sample.feature.map

import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.marcohc.terminator.core.mvi.domain.MviBaseInteractor
import com.marcohc.terminator.core.mvi.test.MviInteractorTest
import com.marcohc.terminator.core.mvi.test.mockComplete
import com.marcohc.terminator.core.mvi.test.mockError
import com.marcohc.terminator.core.mvi.test.mockNever
import com.marcohc.terminator.core.mvi.test.mockValue
import com.marcohc.tier.sample.feature.map.MapInteractor.MapAction
import com.marcohc.tier.sample.feature.map.adapter.VehicleItem
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test
import org.mockito.Mock

internal class MapInteractorTest : MviInteractorTest<MapIntention, MapAction, MapState>() {

    @Mock
    lateinit var saveSelectedVehicleUseCase: SaveSelectedVehicleUseCase

    @Mock
    lateinit var getVehiclesUseCase: GetVehiclesUseCase

    @Mock
    lateinit var router: MapRouter

    @Mock
    lateinit var analytics: MapAnalytics

    override fun provideInteractor(): MviBaseInteractor<MapIntention, MapAction, MapState> = MapInteractor(
        saveSelectedVehicleUseCase = saveSelectedVehicleUseCase,
        getVehiclesUseCase = getVehiclesUseCase,
        router = router,
        analytics = analytics
    )

    @Test
    fun `when initial then track event`() {
        router.requestLocationPermission().mockNever()

        sendIntention(MapIntention.Initial)

        verify(analytics).trackScreen()
    }

    @Test
    fun `when initial then request location permission`() {
        analytics.trackScreen().mockComplete()
        router.requestLastLocation().mockNever()

        sendIntention(MapIntention.Initial)

        verify(router).requestLocationPermission()
    }

    @Test
    fun `when initial then request last location`() {
        analytics.trackScreen().mockComplete()
        router.requestLocationPermission().mockComplete()

        sendIntention(MapIntention.Initial)

        verify(router).requestLastLocation()
    }

    @Test
    fun `given no location when initial then return Loading`() {
        analytics.trackScreen().mockComplete()
        router.requestLocationPermission().mockComplete()
        router.requestLastLocation().mockValue(None)
        getVehiclesUseCase.execute().mockNever()

        sendIntention(MapIntention.Initial)

        assertTypedStateAt<MapState>(1) { loading && !error }
    }

    @Test
    fun `given no location when initial then render items`() {
        val items = listOf<VehicleItem>(mock())
        analytics.trackScreen().mockComplete()
        router.requestLocationPermission().mockComplete()
        router.requestLastLocation().mockValue(None)
        getVehiclesUseCase.execute().mockValue(items)

        sendIntention(MapIntention.Initial)

        assertTypedStateAt<MapState>(2) { !loading && items.size == 1 && items[0] == items.first() }
    }

    @Test
    fun `given location when initial then move camera to location`() {
        analytics.trackScreen().mockComplete()
        router.requestLocationPermission().mockComplete()
        router.requestLastLocation().mockValue(Some(mock()))
        getVehiclesUseCase.execute(any(), any()).mockNever()

        sendIntention(MapIntention.Initial)

        assertTypedStateAt<MapState>(1) { moveToCurrentLocation.consume() != null }
    }

    @Test
    fun `given location when initial then return Loading`() {
        analytics.trackScreen().mockComplete()
        router.requestLocationPermission().mockComplete()
        router.requestLastLocation().mockValue(Some(mock()))
        getVehiclesUseCase.execute(any(), any()).mockNever()

        sendIntention(MapIntention.Initial)

        assertTypedStateAt<MapState>(2) { loading }
    }

    @Test
    fun `given location when initial then render items`() {
        val items = listOf<VehicleItem>(mock())
        analytics.trackScreen().mockComplete()
        router.requestLocationPermission().mockComplete()
        router.requestLastLocation().mockValue(Some(mock()))
        getVehiclesUseCase.execute(any(), any()).mockValue(items)

        sendIntention(MapIntention.Initial)

        assertTypedStateAt<MapState>(3) { !loading && items.size == 1 && items[0] == items.first() }
    }

    @Test
    fun `given any error when initial then return Error`() {
        analytics.trackScreen().mockComplete()
        router.requestLocationPermission().mockComplete()
        router.requestLastLocation().mockValue(None)
        getVehiclesUseCase.execute().mockError()

        sendIntention(MapIntention.Initial)

        assertTypedStateAt<MapState>(2) { !loading && error }
    }

    @Test
    fun `given error when initial then return show error executable`() {
        analytics.trackScreen().mockComplete()
        router.requestLocationPermission().mockComplete()
        router.requestLastLocation().mockValue(None)
        getVehiclesUseCase.execute().mockError()

        sendIntention(MapIntention.Initial)

        assertTypedStateAt<MapState>(2) { showErrorMessageExecutable.isLoaded() }
    }

    @Test
    fun `when item click then track event`() {
        val item = VehicleItem(mock())
        whenever(router.showDetails()).thenReturn(Completable.complete())

        sendIntention(MapIntention.ItemClick(item))

        verify(analytics).trackItemClick()
    }

    @Test
    fun `when item click then go to details`() {
        val item = VehicleItem(mock())
        analytics.trackItemClick().mockComplete()
        saveSelectedVehicleUseCase.execute(any()).mockComplete()

        sendIntention(MapIntention.ItemClick(item))

        verify(router).showDetails()
    }

    @Test
    fun `when error click then track event`() {
        getVehiclesUseCase.execute().mockNever()

        sendIntention(MapIntention.ErrorClick)

        verify(analytics).trackErrorClick()
    }

    @Test
    fun `when error click then return Loading`() {
        analytics.trackErrorClick().mockComplete()
        getVehiclesUseCase.execute().mockNever()

        sendIntention(MapIntention.ErrorClick)

        assertTypedStateAt<MapState>(1) { loading && !error }
    }

    @Test
    fun `when error click then render items`() {
        val items = listOf<VehicleItem>(mock())
        analytics.trackErrorClick().mockComplete()
        getVehiclesUseCase.execute().mockValue(items)

        sendIntention(MapIntention.ErrorClick)

        assertTypedStateAt<MapState>(2) { !loading && items.size == 1 && items[0] == items.first() }
    }

    @Test
    fun `given error when error click then return error`() {
        analytics.trackErrorClick().mockComplete()
        getVehiclesUseCase.execute().mockError()

        sendIntention(MapIntention.ErrorClick)

        assertTypedStateAt<MapState>(2) { !loading && error }
    }
}
