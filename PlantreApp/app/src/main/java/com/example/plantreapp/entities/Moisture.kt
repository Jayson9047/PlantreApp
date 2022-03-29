package com.example.plantreapp.entities

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Moisture (
        @PrimaryKey(autoGenerate = true) val uid: Int?,
        @ColumnInfo(name = "percentage") var percentage: Int,
        @ColumnInfo(name = "text") var text: String,
        @ColumnInfo(name = "btnName") var btnName: String,
        @ColumnInfo(name = "plant_uid") var plantUid: Int // The plant that the moisture sensor is connected to
        /*@PrimaryKey(autoGenerate = true) val uid: Int?,
        @JvmField var percentage: Int,
        @JvmField var text: String,
        @JvmField var btnName: String,
        @JvmField var plantUid: Int // The plant that the moisture sensor is connected to*/
) {
    override fun hashCode(): Int {
        return Objects.hash(uid, percentage, text, btnName, plantUid)
    }
    companion object {
        var itemCallback: DiffUtil.ItemCallback<Moisture> = object : DiffUtil.ItemCallback<Moisture>() {
            override fun areItemsTheSame(oldItem: Moisture, newItem: Moisture): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Moisture, newItem: Moisture): Boolean {
                return oldItem == newItem
            }
        }
    }
}