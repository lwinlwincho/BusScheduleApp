package com.llc.roomdatabaseeg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llc.roomdatabaseeg.database.entity.BusScheduleEntity
import com.llc.roomdatabaseeg.database.dao.ScheduleDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception

class BusScheduleViewModel(private val scheduleDao: ScheduleDao) : ViewModel() {

    private var _busScheduleListEvent = MutableLiveData<BusScheduleListEvent>()
    val busScheduleListEvent: LiveData<BusScheduleListEvent> = _busScheduleListEvent

    init {
        getNowShowing()

    }

    private fun getNowShowing() {

        _busScheduleListEvent.value = BusScheduleListEvent.Loading

        viewModelScope.launch {
            try {
                //get data from offline database
                val result =
                    MovieAPI.retrofitService.getNowPlaying().results.sortedByDescending { it.releaseDate }
                _busScheduleListEvent.value = BusScheduleListEvent.Success(result)
            } catch (e: Exception) {
                _busScheduleListEvent.value = BusScheduleListEvent.Failure(e.message.toString())
            }
        }
    }
}

sealed class BusScheduleListEvent {
    data class Success(val busList: List<BusScheduleEntity>) : BusScheduleListEvent()
    data class Failure(val message: String) : BusScheduleListEvent()
    object Loading : BusScheduleListEvent()
}