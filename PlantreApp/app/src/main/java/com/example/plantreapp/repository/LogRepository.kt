package com.example.plantreapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.plantreapp.dao.LogDAO
import com.example.plantreapp.db.AppDatabase
import com.example.plantreapp.entities.Log

class LogRepository(context: Context) {

    private var dao: LogDAO? = null
    private var logs: LiveData<List<Log>>? = null

    init  {
        val db = AppDatabase.invoke(context)
        dao = db.logDao()
        logs = liveData {
            val data = dao?.getAll()
            if (data != null) {
                emit(data)
            }

        }

    }

    suspend fun getAll() : List<Log> {
        val list: List<Log> = emptyList()
        return dao?.getAll() ?: list
    }

    suspend fun findByName(name: String) : List<Log> {
        val list: List<Log> = emptyList()
        return dao?.findByName(name) ?: list
    }

    suspend fun findById(id: Int) : List<Log> {
        val list: List<Log> = emptyList()
        return dao?.findById(id) ?: list
    }

    suspend fun insert(log: Log) {
        dao?.insert(log)
    }
}