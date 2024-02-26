package com.redstoneapps.spacex.data.repository.launch

import com.redstoneapps.spacex.constants.Resource
import com.redstoneapps.spacex.data.model.LaunchModel
import com.redstoneapps.spacex.network.PlaceHolderService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LaunchRemoteDataSource : LaunchDataSource {
    override fun getLatestLaunch(): Flow<Resource<LaunchModel>> = flow {
        try {
            emit(Resource.Loading())
            val list = PlaceHolderService.build().getLatestLaunch()
            emit(Resource.Success(list.body()))
        } catch (e: Exception) {
            emit(Resource.Error(e))
            e.printStackTrace()
        }
    }
}