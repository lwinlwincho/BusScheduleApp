package com.llc.roomdatabaseeg.bus_full_schedule_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llc.roomdatabaseeg.database.BusDao
import com.llc.roomdatabaseeg.database.BusScheduleEntity
import com.llc.roomdatabaseeg.database.BusRoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BusScheduleViewModel @Inject constructor(
    private val busDao: BusDao
) : ViewModel() {

    private var _busScheduleListEvent = MutableLiveData<BusScheduleListEvent>()
    val busScheduleListEvent: LiveData<BusScheduleListEvent> = _busScheduleListEvent

    fun getAllBusSchedule() {

        viewModelScope.launch {
            try {
                //get data from offline database
                val result: List<BusScheduleEntity> = busDao.getAll()
                _busScheduleListEvent.value = BusScheduleListEvent.Success(result)
            } catch (e: Exception) {
                _busScheduleListEvent.value = BusScheduleListEvent.Failure(e.message.toString())
            }
        }
    }
}

sealed class BusScheduleListEvent {
    object Loading : BusScheduleListEvent()
    data class Success(val busList: List<BusScheduleEntity>) : BusScheduleListEvent()
    data class Failure(val message: String) : BusScheduleListEvent()
}