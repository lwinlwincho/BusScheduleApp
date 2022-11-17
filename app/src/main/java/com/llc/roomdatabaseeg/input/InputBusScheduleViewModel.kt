package com.llc.roomdatabaseeg.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llc.roomdatabaseeg.database.BusDao
import com.llc.roomdatabaseeg.database.BusScheduleEntity
import com.llc.roomdatabaseeg.database.BusRoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputBusScheduleViewModel @Inject constructor(
    private val busDao: BusDao
) : ViewModel() {

    private var _inputUiEvent = MutableLiveData<InputBusScheduleEvent>()
    val inputUiEvent: LiveData<InputBusScheduleEvent> = _inputUiEvent

    fun addBusSchedule(
        busName: String,
        time: String
    ) {
        viewModelScope.launch {
            try {
                val entity = BusScheduleEntity(
                    busName = busName,
                    arrivalTime = time
                )
                busDao.addBusScheduleEntity(entity)
                _inputUiEvent.postValue(InputBusScheduleEvent.Success("Successfully Added!"))
            } catch (e: Exception) {
                _inputUiEvent.postValue(InputBusScheduleEvent.Failure(e.message.toString()))
            }
        }
    }
}

sealed class InputBusScheduleEvent {
    data class Success(val message: String) : InputBusScheduleEvent()
    data class Failure(val message: String) : InputBusScheduleEvent()
}