package com.marcohc.tier.sample.di

import com.marcohc.tier.sample.feature.detail.DetailModule
import com.marcohc.tier.sample.feature.map.MapModule
import org.koin.core.module.Module

object FeaturesModule {

    val modules: List<Module>
        get() = mutableListOf<Module>()
            .apply {
                add(MapModule.module)
                add(DetailModule.module)
            }
}
