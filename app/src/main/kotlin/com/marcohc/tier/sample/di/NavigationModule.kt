package com.marcohc.tier.sample.di

import com.marcohc.terminator.core.koin.CoreModule
import com.marcohc.tier.sample.feature.map.MapNavigator
import com.marcohc.tier.sample.navigation.ApplicationNavigator
import org.koin.dsl.module

object NavigationModule : CoreModule {

    override val module = module {
        factory { ApplicationNavigator() }
        factory<MapNavigator> { get<ApplicationNavigator>() }
    }
}
