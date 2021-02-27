package com.example.mars.ui.rover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mars.BaseFragment
import com.example.mars.R
import com.example.mars.data.model.Photo
import com.example.mars.databinding.FragmentRoverBinding
import com.example.mars.util.Constants
import com.example.mars.util.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoverFragment : BaseFragment<FragmentRoverBinding>(),PhotoListAdapter.PhotoListener,AdapterView.OnItemSelectedListener {

    private val photoListAdapter = PhotoListAdapter()
    private val viewModel:RoverViewModel by viewModels()
    private val arrayListPhoto = ArrayList<Photo>()
    private var selectedCamera:String? = null
    lateinit var selectedRover:String
    private var page = 0
    private var isLastPage = false
    private var isLoading = false
    lateinit var linearLayoutManager : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = super.onCreateView(inflater, container, savedInstanceState)?.rootView

        init()
        prepareFilterSpinner()
        preparePhotoRecyclerView()
        listeners()
        getRovers()

        viewModel.liveDataPhotoList.observe(viewLifecycleOwner, {
            isLoading = false
            it.data?.photos?.let { it1 ->
                if (it1.isEmpty()){
                    page = 0
                }
                arrayListPhoto.addAll(it1)
                photoListAdapter.notifyDataSetChanged()
            }
        })

        return mView
    }

    private fun init(){
        linearLayoutManager= LinearLayoutManager(requireContext())
        selectedRover = resources.getString(R.string.curiosity)
    }

    private fun getRovers(){
        viewModel.getPhotoList(page,selectedRover,selectedCamera)
        isLoading = true
    }

    private fun clearPhotoList(){
        page = 0
        isLastPage = false
        isLoading = false
        arrayListPhoto.clear()
        photoListAdapter.notifyDataSetChanged()
    }

    private fun listeners(){
        dataBinding.radioGroupFilter.setOnCheckedChangeListener { group, checkedId ->
            selectedRover = viewModel.getRoverName(checkedId)
            clearPhotoList()
            getRovers()
        }
        //Pagination'ı yapan kod
        dataBinding.recyclerViewPhoto.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
                page++
                getRovers()
            }
            override fun isLastPage(): Boolean = isLastPage
            override fun isLoading(): Boolean  = isLoading
        })
        dataBinding.imageViewFilter.setOnClickListener {
            dataBinding.spinnerFilter.performClick()
        }
    }

    private fun preparePhotoRecyclerView(){
        photoListAdapter.setPhotoListener(this)
        dataBinding.recyclerViewPhoto.layoutManager = linearLayoutManager
        dataBinding.recyclerViewPhoto.adapter = photoListAdapter
        photoListAdapter.submitList(arrayListPhoto)
    }

    private fun prepareFilterSpinner(){
        val adapterFilterCamera: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.camera,
            R.layout.item_checked_spinner,
        )
        dataBinding.spinnerFilter.adapter = adapterFilterCamera
        dataBinding.spinnerFilter.onItemSelectedListener = this
    }

    override fun getLayoutRes(): Int = R.layout.fragment_rover

    override fun clickPhoto(photo: Photo?) {
        val bundle = bundleOf(Constants.PHOTO to photo)
        findNavController().navigate(R.id.action_fragment_rover_to_dialog_fragment_rover_detail,bundle)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position == 0) {
            selectedCamera = null
        }
        else{
            selectedCamera = resources.getStringArray(R.array.camera)[position]
        }
        clearPhotoList()
        getRovers()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}