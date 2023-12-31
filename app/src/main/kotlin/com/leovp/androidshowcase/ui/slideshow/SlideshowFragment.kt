package com.leovp.androidshowcase.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.leovp.androidbase.framework.BaseFragment
import com.leovp.androidshowcase.R
import com.leovp.androidshowcase.databinding.AppFragmentSlideshowBinding

class SlideshowFragment : BaseFragment<AppFragmentSlideshowBinding>(R.layout.app_fragment_slideshow) {

    override fun getTagName(): String = "SSF"
    private val slideshowViewModel by activityViewModels<SlideshowViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): AppFragmentSlideshowBinding {
        return AppFragmentSlideshowBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }
}
