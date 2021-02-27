package com.example.mars.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Photo(
    val camera: Camera?,
    val earth_date: String?,
    val id: Int?,
    val img_src: String?,
    val rover: Rover?,
    val sol: Int?
):Serializable