package com.example.walmarttechassessment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walmarttechassessment.data.model.Country
import com.example.walmarttechassessment.data.repository.CountryRepository
import com.example.walmarttechassessment.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryListViewModel : ViewModel() {
    private val repository = CountryRepository()

    private val _countries = MutableStateFlow<Resource<List<Country>>>(Resource.Loading)
    val countries: StateFlow<Resource<List<Country>>> = _countries

    fun loadCountries() {
        viewModelScope.launch {
            _countries.value = Resource.Loading
            val result = repository.fetchCountries()
            _countries.value = result
        }
    }
}