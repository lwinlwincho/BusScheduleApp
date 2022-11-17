package com.llc.roomdatabaseeg.bus_name_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llc.roomdatabaseeg.bus_full_schedule_list.BusScheduleListEvent
import com.llc.roomdatabaseeg.database.BusDao
import com.llc.roomdatabaseeg.database.BusScheduleEntity
import com.llc.roomdatabaseeg.database.BusRoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BusNameViewModel @Inject constructor(
    private val busDao: BusDao
) : ViewModel() {

    private var _busNameEvent = MutableLiveData<BusScheduleListEvent>()
    val busNameEvent: LiveData<BusScheduleListEvent> = _busNameEvent

    fun getByBusName(busName:String){
        _busNameEvent.value = BusScheduleListEvent.Loading

        viewModelScope.launch {
            try {
                //get data from offline database
                val result: List<BusScheduleEntity> = busDao.getByBusName(busName)
                _busNameEvent.value = BusScheduleListEvent.Success(result)
            } catch (e: Exception) {
                _busNameEvent.value = BusScheduleListEvent.Failure(e.message.toString())
            }
        }
    }
}