package com.example.walmarttechassessment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.walmarttechassessment.data.Country
import com.example.walmarttechassessment.utils.Resource

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<CountryListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadCountries()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CountryListScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun CountryListScreen(viewModel: CountryListViewModel) {
    val resultState by viewModel.countries.collectAsStateWithLifecycle()

    when (val result = resultState) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${result.message}")
            }
        }

        is Resource.Success -> {
            CountryList(countries = result.data)
        }
    }
}

@Composable
fun CountryList(countries: List<Country>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(countries) { country ->
            CountryItem(country)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CountryItem(country: Country) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${country.name}, ${country.region}")
                Text(text = country.code)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = country.capital)
        }
    }
}
