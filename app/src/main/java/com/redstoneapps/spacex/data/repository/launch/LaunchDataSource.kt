package com.redstoneapps.spacex.data.repository.launch

import com.redstoneapps.spacex.constants.Resource
import com.redstoneapps.spacex.data.model.LaunchModel
import kotlinx.coroutines.flow.Flow

interface LaunchDataSource {
    fun getLatestLaunch(): Flow<Resource<LaunchModel>>
}