package com.marcohc.tier.sample.navigation

import androidx.appcompat.app.AppCompatActivity
import com.marcohc.tier.sample.feature.detail.DetailBottomSheetFragment
import com.marcohc.tier.sample.feature.map.MapNavigator

class ApplicationNavigator : MapNavigator {

    override fun goToDetails(activity: AppCompatActivity) {
        activity.supportFragmentManager.run {
            DetailBottomSheetFragment.newInstance().show(this, DETAILS_FRAGMENT_TAG)
        }
    }

    private companion object {
        private const val DETAILS_FRAGMENT_TAG = "details_fragment_tag"
    }
}
