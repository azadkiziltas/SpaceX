package com.redstoneapps.spacex.network
import com.redstoneapps.spacex.constants.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PlaceHolderService {

    fun build(): PlaceHolderApi {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHtppClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.PLACE_HOLDER_BASE_URL)
            .client(okHtppClient)
            .build()

        return retrofit.create(PlaceHolderApi::class.java)
    }
}