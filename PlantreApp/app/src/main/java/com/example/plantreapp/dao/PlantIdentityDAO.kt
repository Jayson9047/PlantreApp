package com.example.plantreapp.dao


import androidx.room.*
import com.example.plantreapp.entities.Log
import com.example.plantreapp.entities.PlantIdentity

@Dao
interface PlantIdentityDAO {
    @Query("SELECT * FROM PlantIdentity")
    suspend fun getAll(): List<PlantIdentity>

    @Insert
    suspend fun insert(plant: PlantIdentity)


    @Delete
    suspend fun delete(plant: PlantIdentity)

    @Update
    suspend fun update(plant: PlantIdentity)

    @Query("SELECT * FROM PlantIdentity WHERE uid = :id")
    suspend fun findById(id: Int): List<PlantIdentity>

}