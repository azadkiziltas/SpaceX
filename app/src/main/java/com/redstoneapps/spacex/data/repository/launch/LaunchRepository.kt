package com.redstoneapps.spacex.data.repository.launch

import com.redstoneapps.spacex.constants.Resource
import com.redstoneapps.spacex.data.model.LaunchModel
import kotlinx.coroutines.flow.Flow

class LaunchRepository {
    private var launchDataSource: LaunchDataSource? = null

    init {
        launchDataSource = LaunchRemoteDataSource()
    }

    fun getLatestLaunch(): Flow<Resource<LaunchModel>> {
        return launchDataSource!!.getLatestLaunch()
    }
}