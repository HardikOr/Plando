package com.example.plando.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.plando.di.IoDispatcher
import com.example.plando.models.Coord
import com.example.plando.models.Event
import com.example.plando.models.MyDate
import com.example.plando.network.Day
import com.example.plando.repo.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    var editEvent = Event()

    private val _editDate = MutableLiveData<MyDate>(MyDate(null))
    val editDate: LiveData<MyDate> = _editDate

    private val _editLocationString = MutableLiveData<String>("")
    val editLocationString: LiveData<String> = _editLocationString

    private val _editLocation = MutableLiveData<Coord?>(null)
    val editLocation: LiveData<Coord?> = _editLocation

    private val weatherFlow = combine(_editLocation.asFlow(), _editDate.asFlow()) { coord, mydate ->
        coord to mydate!!
    }
    private val _weather = MutableStateFlow<Day>(Day("", ""))
    val weather = _weather.asLiveData()

    init {
        viewModelScope.launch(ioDispatcher) {
            weatherFlow.collect {
                it.first?.let { coord ->
                    if (it.second.date != null) {
//                        Log.d("TEST", "Request weather for $coord || ${it.second}")
                        weatherRepository.getWeatherDay(coord, it.second)?.let { day ->
                            _weather.emit(day)
                        }
                    }
                }
            }
        }

        viewModelScope.launch(ioDispatcher) {
            _editDate.asFlow().collect { editEvent.date = it }
        }
        viewModelScope.launch(ioDispatcher) {
            _editLocationString.asFlow().collect { editEvent.locationString = it }
        }
        viewModelScope.launch(ioDispatcher) {
            _editLocation.asFlow().collect { editEvent.location = it }
        }
        viewModelScope.launch(ioDispatcher) {
            _weather.collect {
                if (it.conditions.isNotEmpty()) editEvent.weather = it.conditions
                if (it.icon.isNotEmpty()) editEvent.weatherIcon = it.icon
            }
        }
    }

    fun setEvent(event: Event) {
        editEvent = event
        _editDate.value = event.date
        _editLocationString.value = event.locationString
        _editLocation.value = event.location
    }

    fun setDate(date: MyDate) {
        _editDate.value = date
    }

    fun setLocation(string: String, coord: Coord) {
        _editLocationString.value = string
        _editLocation.value = coord
    }
}