package com.example.plando.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.plando.di.IoDispatcher
import com.example.plando.models.Event
import com.example.plando.repo.EventRepository
import com.example.plando.repo.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val weatherRepository: WeatherRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(listOf())
    val events = _events.asLiveData()

    init {
        fetchDataFromDB()
    }

    fun fetchDataFromDB() {
        viewModelScope.launch {
            eventRepository.fetchAllEvents().flowOn(ioDispatcher).collect {
                _events.value = it
            }
        }
    }

    fun refreshWeather() {
        viewModelScope.launch {
            val oldEvents = events.value!!
            weatherRepository.getEventsWithWeather(oldEvents).forEach {
                if (it != null) insertEvent(it)
            }
        }
    }

    fun insertEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.insertEvent(event)
        }
    }

    fun deleteEvent(pos: Int) {
        viewModelScope.launch {
            eventRepository.deleteEvent(events.value!![pos])
        }
    }
}