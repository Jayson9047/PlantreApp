package com.example.plantreapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.plantreapp.dao.JournalDAO
import com.example.plantreapp.db.AppDatabase
import com.example.plantreapp.entities.Journal

class JournalRepository(context: Context) {
    private var dao: JournalDAO? = null
    private var journals: LiveData<List<Journal>>? = null

    init  {
        val db = AppDatabase.invoke(context)
        dao = db.journalDao()
        journals = liveData {
            val data = dao?.getAll()
            if (data != null) {
                emit(data)
            }

        }

    }

    suspend fun getAll() : List<Journal> {
        val list: List<Journal> = emptyList()
        return dao?.getAll() ?: list
    }

    suspend fun findByName(name: String) : List<Journal> {
        val list: List<Journal> = emptyList()
        return dao?.findByName(name) ?: list
    }

    suspend fun findById(id: Int) : List<Journal> {
        val list: List<Journal> = emptyList()
        return dao?.findById(id) ?: list
    }

    suspend fun insert(journal: Journal) {
        dao?.insert(journal)
    }


}