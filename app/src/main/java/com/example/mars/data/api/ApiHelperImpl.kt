package com.example.mars.data.api

import com.example.mars.data.model.PhotoList
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService:ApiService):ApiHelper {

    override suspend fun getRoversPhoto(page: Int,rover:String,camera:String?): Response<PhotoList> = apiService.getRoversPhoto(rover,page,camera)

}