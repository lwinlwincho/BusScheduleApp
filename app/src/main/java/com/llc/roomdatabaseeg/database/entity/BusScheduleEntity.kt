package com.llc.roomdatabaseeg.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BusScheduleEntity(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "bus_name") val busName: String,
    @NonNull @ColumnInfo(name = "arrival_time") val arrivalTime: String
)
