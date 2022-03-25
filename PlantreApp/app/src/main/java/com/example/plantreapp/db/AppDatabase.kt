package com.example.plantreapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.plantreapp.dao.*
import com.example.plantreapp.entities.*

@Database(entities = [Plant::class, Journal::class, Log::class, Timer::class, PlantInfo::class], version = 14)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDAO
    abstract fun journalDao(): JournalDAO
    abstract fun logDao(): LogDAO
    abstract fun timerDao(): TimerDAO
    abstract fun plantInfoDao(): PlantInfoDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "local")
            .fallbackToDestructiveMigration().build()
    }
}
