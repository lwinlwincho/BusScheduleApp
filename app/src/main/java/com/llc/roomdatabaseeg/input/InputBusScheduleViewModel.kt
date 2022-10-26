package com.llc.roomdatabaseeg.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llc.roomdatabaseeg.database.entity.BusScheduleEntity
import com.llc.roomdatabaseeg.database.schedule.AppDatabase
import com.llc.roomdatabaseeg.viewmodel.BusScheduleListEvent
import kotlinx.coroutines.launch

class InputBusScheduleViewModel : ViewModel() {

    private var _uiEvent = MutableLiveData<InputBusScheduleEvent>()
    val uiEvent: LiveData<InputBusScheduleEvent> = _uiEvent

    fun addBusSchedule(
        appDatabase: AppDatabase,
        busName: String, time: String
    ) {

        viewModelScope.launch {
            try {
                val entity = BusScheduleEntity(
                    busName = busName,
                    arrivalTime = time
                )
                appDatabase.scheduleDao().addBusScheduleEntity(entity)
                _uiEvent.postValue(InputBusScheduleEvent.Success("Successfully Added!"))
            } catch (e: Exception) {
                _uiEvent.postValue(InputBusScheduleEvent.Failure(e.message.toString()))
            }
        }
    }
}

sealed class InputBusScheduleEvent {
    data class Success(val message: String) : InputBusScheduleEvent()
    data class Failure(val message: String) : InputBusScheduleEvent()
}