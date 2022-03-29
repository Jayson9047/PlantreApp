package com.example.plantreapp.entities
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*



@Entity
data class PlantIdentity (
        @PrimaryKey(autoGenerate = true) val uid:Int?,
        @ColumnInfo(name = "pName") val pName: String?,
        @ColumnInfo(name = "position") val position: Int, // How often to water - in days 1-7, generally seedlings need a higher rate

) {

    override fun hashCode(): Int {
        return Objects.hash(uid, pName, position )
    }
    companion object {
        var itemCallback: DiffUtil.ItemCallback<PlantIdentity> = object : DiffUtil.ItemCallback<PlantIdentity>() {
            override fun areItemsTheSame(oldItem: PlantIdentity, newItem: PlantIdentity): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: PlantIdentity, newItem: PlantIdentity): Boolean {
                return oldItem == newItem
            }
        }
    }

}