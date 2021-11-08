package com.marcohc.tier.sample.di

import com.marcohc.tier.sample.shared.data.di.DataModule
import org.koin.core.module.Module

/**
 * This class contains the list of all modules of the app.
 *
 * Each module could have its own submodules.
 */
object ApplicationModule {

    val modules
        get() = mutableListOf<Module>()
            .apply {
                add(DataModule.module)
                add(NavigationModule.module)
                add(RxModule.module)
                add(SystemInformationModule.module)
                addAll(FeaturesModule.modules)
            }
}
