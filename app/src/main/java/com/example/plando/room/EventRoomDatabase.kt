package com.example.plando.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.plando.models.Event

@Database(entities = [Event::class], version = 1)
@TypeConverters(EventTypeConverters::class)
abstract class EventRoomDatabase : RoomDatabase() {
    abstract fun eventDAO(): EventDAO
}