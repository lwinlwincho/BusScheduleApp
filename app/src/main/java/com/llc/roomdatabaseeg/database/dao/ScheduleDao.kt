package com.llc.roomdatabaseeg.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.llc.roomdatabaseeg.database.entity.BusScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("Select * from busscheduleentity ORDER BY arrival_time ASC")
    fun getAll(): Flow<List<BusScheduleEntity>>

    @Query("Select * from busscheduleentity where bus_name= :busName ORDER BY arrival_time ASC")
    fun getByBusName(busName: String): Flow<List<BusScheduleEntity>>
}