package com.example.walmarttechassessment.data.repository

import com.example.walmarttechassessment.data.model.Country
import com.example.walmarttechassessment.data.network.RetrofitInstance
import com.example.walmarttechassessment.utils.Resource

class CountryRepository {
    suspend fun fetchCountries(): Resource<List<Country>> {
        return try {
            val countries = RetrofitInstance.api.getCountries()
            Resource.Success(countries)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}