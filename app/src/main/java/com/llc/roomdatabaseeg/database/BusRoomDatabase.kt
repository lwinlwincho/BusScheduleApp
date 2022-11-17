package com.llc.roomdatabaseeg.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BusScheduleEntity::class], version = 1)
abstract class BusRoomDatabase : RoomDatabase() {
    abstract fun busDao(): BusDao

   /* companion object {
        @Volatile
        private var INSTANCE: BusRoomDatabase? = null

        fun getDatabase(context: Context): BusRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    BusRoomDatabase::class.java,
                    "main-database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }*/
}