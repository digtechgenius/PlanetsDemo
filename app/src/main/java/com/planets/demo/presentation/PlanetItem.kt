package com.planets.demo.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.planets.demo.domain.Planet

@Composable
fun PlanetItem(
    planet: Planet,
    modifier: Modifier = Modifier,
    onItemClick: (Planet) -> Unit
) {
    Card(
        modifier = modifier
            .testTag("planet_list_item_${planet.id}")
            .clickable { onItemClick(planet) },
        elevation = 4.dp,
    ) {
        Text(
            text = planet.name ?: "Unknown",
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}