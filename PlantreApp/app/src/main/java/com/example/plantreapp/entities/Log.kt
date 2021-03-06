package com.example.plantreapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Log (
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "journal_UID") val journalUID: Int,
    @ColumnInfo(name = "date_created") val dateCreated: String,
    @ColumnInfo(name = "description") val description: String, // Small description, display in list ?
    @ColumnInfo(name = "note") val note: String, // Note to be displayed while note is open - more descriptive text
)