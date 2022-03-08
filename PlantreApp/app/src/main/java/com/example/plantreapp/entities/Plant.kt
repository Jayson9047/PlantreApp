package com.example.plantreapp.entities

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Plant (
    @PrimaryKey(autoGenerate = true) val uid:Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "scientific_name") val scientificName: String?,
    @ColumnInfo(name = "picture") val picture: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "stage") val stage: String?, // seed - seedling - mature
    @ColumnInfo(name = "seed_water_rate") val seedWaterRate: Int, // How often to water - in days 1-7, generally seedlings need a higher rate
    @ColumnInfo(name = "seedling_water_rate") val seedlingWaterRate: Int, // How often to water - in days 1-7, generally seedlings need a higher rate
    @ColumnInfo(name = "mature_water_rate") val matureWaterRate: Int, // How often to water - in days 1-7
    @ColumnInfo(name = "min_seed_moisture") val minSeedMoisture: Float, // minimum moisture level - seed
    @ColumnInfo(name = "max_seed_moisture")  val maxSeedMoisture: Float, // maximum moisture level - seed
    @ColumnInfo(name = "min_seedling_moisture") val minSeedlingMoisture: Float, // minimum moisture level - seedling
    @ColumnInfo(name = "max_seedling_moisture")  val maxSeedlingMoisture: Float, // maximum moisture level - seedling
    @ColumnInfo(name = "min_mature_moisture") val minMatureMoisture: Float, // minimum moisture level - Mature
    @ColumnInfo(name = "max_mature_moisture")  val maxMatureMoisture: Float // maximum moisture level - Mature
) {

    override fun hashCode(): Int {
        return Objects.hash(uid, name, description)
    }
    companion object {
        var itemCallback: DiffUtil.ItemCallback<Plant> = object : DiffUtil.ItemCallback<Plant>() {
            override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
                return oldItem == newItem
            }
        }
    }

}
