package com.example.plantreapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Timer (
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "plant_uid") val plantUID: Int, // Change this to change the relationship - should it be on a journal, log, plant basis?
    @ColumnInfo(name = "water_rate") val waterRate: Float,
    @ColumnInfo(name = "last_notified") val lastNotified: String,
    @ColumnInfo(name = "date_created") val dateCreated: String,

)