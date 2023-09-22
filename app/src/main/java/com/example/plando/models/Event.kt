package com.example.plando.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,

    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "description") var description: String = "",
    @ColumnInfo(name = "date") var date: MyDate = MyDate(null),
    @ColumnInfo(name = "location") var location: Coord? = null,
    @ColumnInfo(name = "locationString") var locationString: String = "",
    @ColumnInfo(name = "locationAlias") var locationAlias: String = "",
    @ColumnInfo(name = "hasAttended") var hasAttended: Boolean = false,

    @ColumnInfo(name = "weather")
    var weather: String? = null,
    @ColumnInfo(name = "weatherIcon")
    var weatherIcon: String? = null
) : Serializable