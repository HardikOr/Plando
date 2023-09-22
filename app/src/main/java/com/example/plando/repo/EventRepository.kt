package com.example.plando.repo

import com.example.plando.di.IoDispatcher
import com.example.plando.models.Event
import com.example.plando.room.EventDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDAO,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchAllEvents() = withContext(ioDispatcher) {
        eventDao.getAllEventsSorted()
    }

    suspend fun insertEvent(event: Event) = withContext(ioDispatcher) {
        eventDao.insert(event)
    }

    suspend fun deleteEvent(event: Event) = withContext(ioDispatcher) {
        eventDao.delete(event)
    }
}