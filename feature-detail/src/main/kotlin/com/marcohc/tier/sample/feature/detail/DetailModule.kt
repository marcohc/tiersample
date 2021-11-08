package com.marcohc.tier.sample.feature.detail

import com.marcohc.terminator.core.koin.FeatureModule
import com.marcohc.terminator.core.mvi.ext.declareFragmentInteractor
import org.koin.dsl.module

object DetailModule : FeatureModule {

    override val scopeId: String = "DetailModule"

    override val module = module {

        declareFragmentInteractor(scopeId) {
            DetailInteractor(
                getSelectedVehicleUseCase = GetSelectedVehicleUseCase(repository = get()),
                analytics = DetailAnalytics()
            )
        }
    }
}
