package com.example.plando.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plando.models.Coord
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapViewModel : ViewModel() {
    private var googleFetchJob: Job? = null

    private val _fetchedAddress = MutableLiveData<Pair<String, Coord>?>(null)
    val fetchedAddress: LiveData<Pair<String, Coord>?> = _fetchedAddress

    fun setAddressAsync(address: String, coord: Coord) {
        _fetchedAddress.postValue(address to coord)
    }

    fun fetchGeocoderData(fetch: () -> Unit) {
        googleFetchJob?.cancel()
        googleFetchJob = viewModelScope.launch(CoroutineName("Geocoder fetch")) {
            withContext(Dispatchers.IO) {
                fetch.invoke()
            }
        }
    }
}