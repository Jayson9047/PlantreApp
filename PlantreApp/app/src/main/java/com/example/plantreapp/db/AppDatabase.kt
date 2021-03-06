package com.example.plantreapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantreapp.dao.JournalDAO
import com.example.plantreapp.dao.LogDAO
import com.example.plantreapp.dao.PlantDAO
import com.example.plantreapp.entities.Journal
import com.example.plantreapp.entities.Log
import com.example.plantreapp.entities.Plant

@Database(entities = [Plant::class, Journal::class, Log::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDAO
    abstract fun journalDao(): JournalDAO
    abstract fun logDao(): LogDAO

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
