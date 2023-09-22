package com.example.plando.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plando.models.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDAO {
    @Query("SELECT * FROM event_table ORDER BY date")
    fun getAllEventsSorted(): Flow<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Delete
    fun delete(event: Event)
}