package com.redstoneapps.spacex.network


import com.redstoneapps.spacex.data.model.LaunchModel
import retrofit2.Response
import retrofit2.http.GET

interface PlaceHolderApi {
    @GET("launches/latest")
    suspend fun getLatestLaunch(
    ): Response<LaunchModel>
}
