package com.marcohc.tier.sample.feature.detail

import android.os.Bundle
import com.marcohc.terminator.core.mvi.ui.MviBottomSheetDialogFragment
import com.marcohc.terminator.core.mvi.ui.MviConfig
import com.marcohc.terminator.core.mvi.ui.MviConfigType
import com.marcohc.terminator.core.utils.unsafeLazy
import com.marcohc.tier.sample.feature.detail.databinding.DetailFragmentBottomSheetBinding

class DetailBottomSheetFragment : MviBottomSheetDialogFragment<DetailIntention, DetailState>() {

    override val mviConfig: MviConfig = MviConfig(
        scopeId = DetailModule.scopeId,
        layoutId = R.layout.detail_fragment_bottom_sheet,
        mviConfigType = MviConfigType.NO_SCOPE
    )

    private val viewBinding by unsafeLazy { DetailFragmentBottomSheetBinding.bind(inflatedView) }

    override fun afterComponentCreated(savedInstanceState: Bundle?) {
        sendIntention(DetailIntention.Initial)
    }

    override fun render(state: DetailState) {
        with(state) {
            viewBinding.modelText.text = model
            viewBinding.zoneText.text = zone
            viewBinding.batteryText.text = batteryFormatted
            viewBinding.stateText.text = this.state
        }
    }

    companion object {
        fun newInstance(): DetailBottomSheetFragment {
            return DetailBottomSheetFragment()
        }
    }
}
