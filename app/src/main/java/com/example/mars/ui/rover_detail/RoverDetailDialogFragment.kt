package com.example.mars.ui.rover_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.mars.BaseDialogFragment
import com.example.mars.R
import com.example.mars.data.model.Photo
import com.example.mars.databinding.DialogFragmentRoverDetailBinding
import com.example.mars.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoverDetailDialogFragment : BaseDialogFragment<DialogFragmentRoverDetailBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = super.onCreateView(inflater, container, savedInstanceState)?.rootView

        dataBinding.photo = arguments?.getSerializable(Constants.PHOTO) as Photo

        return mView
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun getLayoutRes(): Int = R.layout.dialog_fragment_rover_detail
}