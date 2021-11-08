package com.marcohc.tier.sample.di

import com.marcohc.terminator.core.koin.CoreModule
import com.marcohc.tier.sample.BuildConfig
import com.marcohc.tier.sample.shared.data.di.DataModule.API_KEY
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SystemInformationModule : CoreModule {

    override val module = module {
        single(named(API_KEY)) { BuildConfig.API_KEY }
    }
}
