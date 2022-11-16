package com.llc.roomdatabaseeg.database

import android.app.Application

class BusScheduleApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}