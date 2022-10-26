package com.llc.roomdatabaseeg.database.schedule

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.llc.roomdatabaseeg.database.dao.ScheduleDao
import com.llc.roomdatabaseeg.database.entity.BusScheduleEntity

@Database(entities = arrayOf(BusScheduleEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance= Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "appDatabase")
                    .build()
                INSTANCE=instance

                instance
            }
        }
    }
}