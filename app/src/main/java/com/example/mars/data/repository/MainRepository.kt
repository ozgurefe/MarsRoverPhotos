package com.example.mars.data.repository

import com.example.mars.data.model.PhotoList
import com.example.mars.data.api.ApiHelper
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getRoversPhoto(page: Int,rover:String,camera:String?): Response<PhotoList> = apiHelper.getRoversPhoto(page,rover,camera)
}