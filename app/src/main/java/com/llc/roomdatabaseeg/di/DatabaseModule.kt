package com.llc.roomdatabaseeg.di

import android.content.Context
import androidx.room.Room
import com.llc.roomdatabaseeg.database.BusDao
import com.llc.roomdatabaseeg.database.BusRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {

    @Provides
    @ViewModelScoped
    fun provideMovieRoomDatabase(@ApplicationContext context: Context): BusRoomDatabase {
        return Room.databaseBuilder(
            context,
            BusRoomDatabase::class.java,
            "main_database"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideMovieDao(busRoomDatabase: BusRoomDatabase): BusDao {
        return busRoomDatabase.busDao()
    }
}