package com.example.plantreapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.plantreapp.dao.PlantDAO
import com.example.plantreapp.db.AppDatabase
import com.example.plantreapp.entities.Plant
import kotlinx.coroutines.runBlocking

class PlantRepository(context: Context) {
    private var dao: PlantDAO? = null
    var plants: MutableLiveData<List<Plant>> = MutableLiveData()

    init  {
        val db = AppDatabase.invoke(context)
        dao = db.plantDao()
        runBlocking {
            plants.value = dao?.getAll()
        }
    }

    suspend fun getAll() : List<Plant>? {
        val list: List<Plant> = emptyList()
        return dao?.getAll() ?: list
    }

    suspend fun findByName(name: String) : List<Plant> {
        val list: List<Plant> = emptyList()
        return dao?.findByName(name) ?: list
    }

    suspend fun findById(id: Int) : List<Plant> {
        val list: List<Plant> = emptyList()
        return dao?.findById(id) ?: list
    }

    suspend fun insert(plant: Plant) {
       runBlocking { dao?.insert(plant) }
        runBlocking {
            plants.value = dao?.getAll()
        }
    }

    suspend fun delete(plant: Plant) {
        runBlocking { dao?.delete(plant) }
        runBlocking {
            plants.value = dao?.getAll()
        }
    }
}
