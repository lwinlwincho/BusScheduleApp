package com.llc.roomdatabaseeg.bus_full_schedule_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llc.roomdatabaseeg.database.entity.BusScheduleEntity
import com.llc.roomdatabaseeg.database.schedule.AppDatabase
import kotlinx.coroutines.launch
import java.lang.Exception

class BusScheduleViewModel : ViewModel() {

    private var _busScheduleListEvent = MutableLiveData<BusScheduleListEvent>()
    val busScheduleListEvent: LiveData<BusScheduleListEvent> = _busScheduleListEvent

    fun getAllBusSchedule(appDatabase: AppDatabase) {
        _busScheduleListEvent.value = BusScheduleListEvent.Loading

        viewModelScope.launch {
            try {
                //get data from offline database
                val result: List<BusScheduleEntity> = appDatabase.scheduleDao().getAll()
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