package com.example.walmarttechassessment.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.walmarttechassessment.data.model.Country
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Something went wrong.\n${result.message}")
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { viewModel.loadCountries() }) {
                        Text("Try Again")
                    }
                }
            }
        }


        is Resource.Success -> {
            CountryList(countries = result.data)
        }
    }
}

@Composable
fun CountryList(countries: List<Country>) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(countries) { country ->
            CountryItem(country = country, onClick = {
                Toast.makeText(
                    context,
                    "${country.name} selected",
                    Toast.LENGTH_SHORT
                ).show()
            })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CountryItem(country: Country, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
