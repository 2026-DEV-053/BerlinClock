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
import androidx.compose.ui.unit.Dp
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

        LampRow(
            lamps = clockState.fiveHourRow,
            testTag = "FiveHourLamp"
        )

        Spacer(Modifier.height(16.dp))

        LampRow(
            lamps = clockState.oneHourRow,
            testTag = "OneHourLamp"
        )

        Spacer(Modifier.height(16.dp))

        FiveMinuteLampRow(
            lamps = clockState.fiveMinuteRow
        )

        Spacer(Modifier.height(16.dp))

        LampRow(
            lamps = clockState.oneMinuteRow,
            testTag = "OneMinuteLamp"
        )
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
private fun LampRow(
    lamps: List<BerlinClockLamp>,
    testTag: String
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .testTag(testTag),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        lamps.forEachIndexed { index, lamp ->

            Lamp(
                modifier = Modifier.weight(1f),
                on = lamp.isOn,
                color = Color.Red,
                height = 48.dp,
                first = index == 0,
                last = index == lamps.lastIndex
            )
        }
    }
}

@Composable
private fun FiveMinuteLampRow(
    lamps: List<BerlinClockLamp>
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .testTag("FiveMinuteLamp"),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        lamps.forEachIndexed { index, lamp ->

            val color =
                if ((index + 1) % 3 == 0)
                    Color.Red
                else
                    Color.Yellow

            Lamp(
                modifier = Modifier.weight(1f),
                on = lamp.isOn,
                color = color,
                height = 48.dp,
                first = index == 0,
                last = index == lamps.lastIndex
            )
        }
    }
}

@Composable
private fun Lamp(
    modifier: Modifier,
    on: Boolean,
    color: Color,
    height: Dp,
    first: Boolean,
    last: Boolean
) {

    val shape = when {
        first ->
            RoundedCornerShape(
                topStart = 22.dp,
                bottomStart = 22.dp
            )

        last ->
            RoundedCornerShape(
                topEnd = 22.dp,
                bottomEnd = 22.dp
            )

        else ->
            RectangleShape
    }

    Box(
        modifier = modifier
            .height(height)
            .clip(shape)
            .background(
                if (on) color else Color.White
            )
            .border(
                3.dp,
                Color(0xFF555555),
                shape
            )
    )
}