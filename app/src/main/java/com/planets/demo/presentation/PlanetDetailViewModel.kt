package com.planets.demo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planets.demo.domain.Planet
import com.planets.demo.domain.PlanetInteractorImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetDetailViewModel @Inject constructor(
    private val interactor: PlanetInteractorImplementation
) : ViewModel() {
    private val _planetDetails = MutableLiveData<Planet?>()
    val planetDetails: LiveData<Planet?> = _planetDetails

    fun fetchPlanetDetails(planetId: String) {
        viewModelScope.launch {
            interactor.getPlanetById(planetId).collect { fetchedPlanet ->
                _planetDetails.value = fetchedPlanet
            }
        }
    }
}