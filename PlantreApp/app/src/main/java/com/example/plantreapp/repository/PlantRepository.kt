package com.example.plantreapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.plantreapp.dao.PlantDAO
import com.example.plantreapp.db.AppDatabase
import com.example.plantreapp.entities.Plant

class PlantRepository(context: Context) {
    private var dao: PlantDAO? = null
    private var plants: LiveData<List<Plant>>? = null

    init  {
        val db = AppDatabase.invoke(context)
        dao = db.plantDao()
        plants = liveData {
            val data = dao?.getAll()
            if (data != null) {
                emit(data)
            }
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
        dao?.insert(plant)
    }

    suspend fun insertAll(plants: List<Plant>) {
        dao?.insertAll(plants)
    }
}