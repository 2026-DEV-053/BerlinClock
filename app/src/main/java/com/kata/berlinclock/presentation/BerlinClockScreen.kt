package com.kata.berlinclock.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kata.berlinclock.domain.model.BerlinClockLamp
import com.kata.berlinclock.domain.model.BerlinClockState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerlinClockScreen(
    viewModel: BerlinClockViewModel = hiltViewModel()
){
    val clockState = viewModel.clockState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startUpdatingClock()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        BerlinClockDisplay(
            clockState = clockState.value,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BerlinClockDisplay(
    clockState: BerlinClockState,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        SecondLamp(clockState.secondsLamp)

        Spacer(Modifier.height(24.dp))

        FiveHourLampRow(lamps = clockState.fiveHourRow)

        Spacer(Modifier.height(16.dp))

        OneHourLampRow(lamps = clockState.oneHourRow)
    }
}

@Composable
private fun SecondLamp(
    lamp: BerlinClockLamp
) {
    val background = if(!lamp.isOn) Color.White else Color.Yellow

    Box(
        modifier = Modifier
            .testTag("SecondsLamp")
            .size(100.dp)
            .border(
                3.dp,
                Color.DarkGray,
                CircleShape
            )
            .background(background, CircleShape)
    )
}

@Composable
private fun FiveHourLampRow(
    lamps: List<BerlinClockLamp>
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .testTag("FiveHourLamp"),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        lamps.forEachIndexed { index, lamp ->

            val shape = when (index) {
                0 ->
                    RoundedCornerShape(
                        topStart = 22.dp,
                        bottomStart = 22.dp
                    )

                lamps.lastIndex ->
                    RoundedCornerShape(
                        topEnd = 22.dp,
                        bottomEnd = 22.dp
                    )

                else -> RectangleShape
            }
            Box(
                modifier = Modifier.weight(1f)
                    .height(62.dp)
                    .clip(shape)
                    .background(
                        if (lamp.isOn) Color.Red else Color.White
                    )
                    .border(
                        3.dp,
                        Color(0xFF555555),
                        shape
                    )
            )
        }
    }
}

@Composable
private fun OneHourLampRow(
    lamps: List<BerlinClockLamp>
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .testTag("OneHourLamp"),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        lamps.forEachIndexed { index, lamp ->

            val shape = when (index) {
                0 ->
                    RoundedCornerShape(
                        topStart = 22.dp,
                        bottomStart = 22.dp
                    )

                lamps.lastIndex ->
                    RoundedCornerShape(
                        topEnd = 22.dp,
                        bottomEnd = 22.dp
                    )

                else -> RectangleShape
            }
            Box(
                modifier = Modifier.weight(1f)
                    .height(62.dp)
                    .clip(shape)
                    .background(
                        if (lamp.isOn) Color.Red else Color.White
                    )
                    .border(
                        3.dp,
                        Color(0xFF555555),
                        shape
                    )
            )
        }
    }
}