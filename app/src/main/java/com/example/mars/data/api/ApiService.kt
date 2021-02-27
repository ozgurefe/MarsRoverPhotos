package com.example.mars.data.api


import com.example.mars.data.model.PhotoList
import com.example.mars.util.Constants
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/v1/rovers/{rover}/photos?sol=1000&api_key=${Constants.API_KEY}")
    suspend fun getRoversPhoto(@Path("rover") rover:String, @Query("page") page:Int, @Query("camera") camera:String?):Response<PhotoList>

}