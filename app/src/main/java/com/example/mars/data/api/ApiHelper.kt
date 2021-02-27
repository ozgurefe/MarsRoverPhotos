package com.example.mars.data.api

import com.example.mars.data.model.PhotoList
import retrofit2.Response

interface ApiHelper {
    suspend fun getRoversPhoto(page:Int,rover:String,camera:String?): Response<PhotoList>
}