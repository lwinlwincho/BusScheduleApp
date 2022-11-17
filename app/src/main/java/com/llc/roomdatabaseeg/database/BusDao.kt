package com.llc.roomdatabaseeg.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BusDao {
    @Query("Select * from busscheduleentity ORDER BY arrival_time ASC")
    fun getAll(): List<BusScheduleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addBusScheduleEntity(clothing: BusScheduleEntity)

    @Query("Select * from busscheduleentity where bus_name= :busName ORDER BY arrival_time ASC")
    fun getByBusName(busName: String): List<BusScheduleEntity>
}