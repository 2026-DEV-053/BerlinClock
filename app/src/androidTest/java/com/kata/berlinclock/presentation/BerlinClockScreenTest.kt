package com.kata.berlinclock.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.kata.berlinclock.domain.model.BerlinClockLamp
import com.kata.berlinclock.domain.model.BerlinClockState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class BerlinClockScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun verifyStartUpdatingClockIsCalledOnComposition() {

        val state = BerlinClockState(
            secondsLamp = BerlinClockLamp.yellow(true),
            fiveHourRow = List(4) { BerlinClockLamp.red(false) },
            oneHourRow = List(4) { BerlinClockLamp.red(false) },
            fiveMinuteRow = List(11) { BerlinClockLamp.off() },
            oneMinuteRow = List(4) { BerlinClockLamp.off() }
        )

        val stateFlow = MutableStateFlow(state)

        val testViewModel = mockk<BerlinClockViewModel>(relaxed = true)

        every { testViewModel.clockState } returns stateFlow

        composeTestRule.setContent {
            BerlinClockScreen(viewModel = testViewModel)
        }

        composeTestRule.waitForIdle()

        verify(exactly = 1) {
            testViewModel.startUpdatingClock()
        }
    }

    @Test
    fun verifySecondsLampIsDisplayed() {

        val state = BerlinClockState(
            secondsLamp = BerlinClockLamp.yellow(true),
            fiveHourRow = List(4) { BerlinClockLamp.red(false) },
            oneHourRow = List(4) { BerlinClockLamp.red(false) },
            fiveMinuteRow = List(11) { BerlinClockLamp.off() },
            oneMinuteRow = List(4) { BerlinClockLamp.off() }
        )

        composeTestRule.setContent {
            BerlinClockDisplay(
                clockState = state,
                modifier = Modifier
            )
        }

        composeTestRule
            .onNodeWithTag("SecondsLamp")
            .assertIsDisplayed()
    }

    @Test
    fun verifyFiveHourLampIsDisplayed() {

        val state = BerlinClockState(
            secondsLamp = BerlinClockLamp.off(),
            fiveHourRow = List(4) { BerlinClockLamp.red(true) },
            oneHourRow = List(4) { BerlinClockLamp.red(false) },
            fiveMinuteRow = List(11) { BerlinClockLamp.off() },
            oneMinuteRow = List(4) { BerlinClockLamp.off() }
        )

        composeTestRule.setContent {
            BerlinClockDisplay(
                clockState = state,
                modifier = Modifier
            )
        }

        composeTestRule
            .onNodeWithTag("FiveHourLamp")
            .assertIsDisplayed()
    }

    @Test
    fun verifyOneHourLampIsDisplayed() {

        val state = BerlinClockState(
            secondsLamp = BerlinClockLamp.off(),
            fiveHourRow = List(4) { BerlinClockLamp.red(false) },
            oneHourRow = List(4) { BerlinClockLamp.red(true) },
            fiveMinuteRow = List(11) { BerlinClockLamp.off() },
            oneMinuteRow = List(4) { BerlinClockLamp.off() }
        )

        composeTestRule.setContent {
            BerlinClockDisplay(
                clockState = state,
                modifier = Modifier
            )
        }

        composeTestRule
            .onNodeWithTag("OneHourLamp")
            .assertIsDisplayed()
    }

    @Test
    fun verifyFiveMinuteLampIsDisplayed() {

        val state = BerlinClockState(
            secondsLamp = BerlinClockLamp.off(),
            fiveHourRow = List(4) { BerlinClockLamp.red(false) },
            oneHourRow = List(4) { BerlinClockLamp.red(false) },
            fiveMinuteRow = List(11) { BerlinClockLamp.yellow(true) },
            oneMinuteRow = List(4) { BerlinClockLamp.off() }
        )

        composeTestRule.setContent {
            BerlinClockDisplay(
                clockState = state,
                modifier = Modifier
            )
        }

        composeTestRule
            .onNodeWithTag("FiveMinuteLamp")
            .assertIsDisplayed()
    }
}
