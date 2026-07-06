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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kata.berlinclock.domain.model.BerlinClockLamp
import com.kata.berlinclock.domain.model.BerlinClockState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerlinClockScreen(
    viewModel: BerlinClockViewModel = hiltViewModel()
){
    // Observe the current Berlin Clock state from the ViewModel.
    val clockState = viewModel.clockState.collectAsState()

    // Start updating the clock when this screen enters the composition.
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
    // Display the Berlin Clock using its traditional four lamp rows
    // followed by the equivalent 24-hour digital time.
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(24.dp))

        // Top seconds' indicator.
        SecondLamp(clockState.secondsLamp)

        Spacer(Modifier.height(24.dp))

        // Five-hour row (4 lamps).
        LampRow(
            lamps = clockState.fiveHourRow,
            testTag = "FiveHourLamp"
        )

        Spacer(Modifier.height(16.dp))

        // One-hour row (4 lamps).
        LampRow(
            lamps = clockState.oneHourRow,
            testTag = "OneHourLamp"
        )

        Spacer(Modifier.height(16.dp))

        // Five-minute row (11 lamps with quarter-hour markers).
        FiveMinuteLampRow(
            lamps = clockState.fiveMinuteRow
        )

        Spacer(Modifier.height(16.dp))

        // One-minute row (4 lamps).
        LampRow(
            lamps = clockState.oneMinuteRow,
            testTag = "OneMinuteLamp"
        )

        Spacer(Modifier.height(24.dp))

        // Digital representation of the same time.
        Text(
            text = clockState.timeInput.toDisplayableDigitalTime(),
            modifier = Modifier.testTag("24HourDigitalTime"),
            fontSize = 72.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            color = Color.Black
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SecondLamp(
    lamp: BerlinClockLamp
) {
    // The seconds lamp blinks on even seconds.
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
    // Generic row used for hour lamps and one-minute lamps.
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
    // Every third lamp represents a quarter-hour and is displayed in red.
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
    // Round only the outer corners to give each row a continuous appearance.
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