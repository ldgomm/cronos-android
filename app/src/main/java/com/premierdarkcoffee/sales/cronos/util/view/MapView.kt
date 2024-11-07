package com.premierdarkcoffee.sales.cronos.util.view

//
//  MapView.kt
//  Maia
//
//  Created by José Ruiz on 1/8/24.
//

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(location: LatLng) {
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude), 13f)
    }

    Column(verticalArrangement = Arrangement.Bottom,
           horizontalAlignment = Alignment.CenterHorizontally,
           modifier = Modifier.padding(16.dp)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)) {
            GoogleMap(modifier = Modifier.fillMaxSize(),
                      cameraPositionState = cameraPositionState,
                      onMapLoaded = { isMapLoaded = true },
                      uiSettings = MapUiSettings(zoomControlsEnabled = false)) {
                Marker(state = MarkerState(location),
                       title = "User location",
                       icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            }

            if (!isMapLoaded) {
                CircularProgressIndicator(modifier = Modifier
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                    .align(Alignment.Center))
            }
        }
    }
}
