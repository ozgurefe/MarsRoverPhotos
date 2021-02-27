package com.example.mars.ui.rover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mars.R
import com.example.mars.data.model.PhotoList
import com.example.mars.data.repository.MainRepository
import com.example.mars.util.Constants
import com.example.mars.util.Resource
import com.example.mars.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoverViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _liveDataPhotoList = MutableLiveData<Resource<PhotoList>>()
    val liveDataPhotoList : LiveData<Resource<PhotoList>> get() = _liveDataPhotoList

    fun getPhotoList(page:Int,rover:String,camera:String?){
        viewModelScope.launch {
            mainRepository.getRoversPhoto(page,rover,camera).let {
                if (it.isSuccessful) {
                    _liveDataPhotoList.value = Resource(Status.SUCCESS, it.body(), "")
                }
                else{
                    _liveDataPhotoList.value = Resource.error(it.message(),null)
                }
            }
        }
    }

    fun getRoverName(radioButtonId: Int):String{
        return when (radioButtonId) {
            R.id.radioButtonCuriosity -> Constants.Curiosity
            R.id.radioButtonOpportunity -> Constants.Opportunity
            R.id.radioButtonSpirit -> Constants.Spirit
            else -> Constants.Curiosity
        }
    }

}