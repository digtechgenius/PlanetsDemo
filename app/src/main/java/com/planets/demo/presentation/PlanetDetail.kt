package com.planets.demo.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.planets.demo.domain.Planet

private const val MISSING_VALUE = "Unknown"

@Composable
fun PlanetDetail(planetDetails: Planet) {
    LazyColumn(
        modifier = Modifier.padding(16.dp).testTag("planet_detail_screen")
    ) {
        item {
            Text(text = "Planet Name: ${planetDetails.name ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Rotation Period: ${planetDetails.rotationPeriod ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Orbital Period: ${planetDetails.orbitalPeriod ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Diameter: ${planetDetails.diameter ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Climate: ${planetDetails.climate ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Gravity: ${planetDetails.gravity ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Terrain: ${planetDetails.terrain ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Surface Water: ${planetDetails.surfaceWater ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Population: ${planetDetails.population ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Residents: ${planetDetails.residents?.joinToString(", ") ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Films: ${planetDetails.films?.joinToString(", ") ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Created: ${planetDetails.created ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Edited: ${planetDetails.edited ?: MISSING_VALUE}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "URL: ${planetDetails.url ?: MISSING_VALUE}")
        }
    }
}