package com.marcohc.tier.sample.feature.map

import com.marcohc.terminator.core.koin.FeatureModule
import com.marcohc.terminator.core.mvi.ext.declareActivityInteractor
import com.marcohc.terminator.core.mvi.ext.declareFactoryActivityRouter
import org.koin.dsl.module

object MapModule : FeatureModule {

    override val scopeId = "MapModule"

    override val module = module {

        declareFactoryActivityRouter(scopeId = scopeId) { navigationExecutor ->
            MapRouter(
                navigator = get(),
                navigationExecutor = navigationExecutor
            )
        }

        declareActivityInteractor(scopeId = scopeId) {
            MapInteractor(
                saveSelectedVehicleUseCase = SaveSelectedVehicleUseCase(repository = get()),
                getVehiclesUseCase = GetVehiclesUseCase(repository = get()),
                router = get(),
                analytics = MapAnalytics(),
            )
        }
    }
}
