package com.redstoneapps.spacex.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movie.util.constants.ResourceStatus
import com.redstoneapps.spacex.data.model.LaunchModel
import com.redstoneapps.spacex.data.repository.launch.LaunchRepository
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

class HomePageViewModel : ViewModel() {

    private val launchRepository: LaunchRepository = LaunchRepository()
    var launchLiveData = MutableLiveData<LaunchModel>()
    var error = MutableLiveData<Throwable>()
    var loading = MutableLiveData<Boolean>()

    init {
        getLaunchData()
    }

    fun getLaunchData() = viewModelScope.launch {
        launchRepository.getLatestLaunch()
            .asLiveData(viewModelScope.coroutineContext).observeForever {
                when (it.status) {
                    ResourceStatus.LOADING -> {
                        loading.postValue(true)
                    }

                    ResourceStatus.SUCCESS -> {
                        launchLiveData.postValue(it.data!!)
                        loading.postValue(false)
                    }

                    ResourceStatus.ERROR -> {
                        error.postValue(it.throwable!!)
                        loading.postValue(false)
                    }
                }
            }
    }


    fun convertTime(time: Int): String {
        val timeStamp = Timestamp(time.toLong() * 1000L)
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.format(timeStamp)
    }

}