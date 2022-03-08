package com.example.plantreapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.plantreapp.dao.JournalDAO
import com.example.plantreapp.db.AppDatabase
import com.example.plantreapp.entities.Journal
import kotlinx.coroutines.runBlocking

class JournalRepository(context: Context) {
    private var dao: JournalDAO? = null
    var journals: MutableLiveData<List<Journal>> = MutableLiveData()

    init  {
        val db = AppDatabase.invoke(context)
        dao = db.journalDao()
        runBlocking {
            journals.value = dao?.getAll()
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
        runBlocking { dao?.insert(journal) }
        runBlocking {
            journals.value = dao?.getAll()
        }
    }

    suspend fun delete(journal: Journal) {
        runBlocking { dao?.delete(journal) }
        runBlocking {
            journals.value = dao?.getAll()
        }
    }

}