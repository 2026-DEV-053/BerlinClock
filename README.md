# Berlin Clock - Android Exercise

## Overview

The Berlin Clock is an unconventional clock that represents time using illuminated lamps instead of traditional digits. This Android application displays a visual representation of the Berlin Clock and updates in **real-time** as the device time changes.

This distinctive clock is read from top to bottom.

* **Top lamp:** A single **yellow** lamp that blinks **every second**.
* **First row:** Four **red** lamps, with each lamp representing **5 hours**.
* **Second row:** Four **red** lamps, with each lamp representing **1 hour**.
* **Third row:** Eleven lamps (**yellow**, with every third lamp **red**), where each lamp represents **5 minutes**.
* **Fourth row:** Four **yellow** lamps, with each lamp representing **1 minute**.

## Prerequisites

Before you begin, ensure you have the following installed:
* **[Android Studio](https://android.com "Android Studio official download")** (Latest stable version recommended)
* **[Git](https://git-scm.com "Git official website")** installed and configured on your system

###  How to run the app

1. In Android Studio, create a new project by selecting the "Project from Version Control" option, then clone the project using the following repository link.
    https://github.com/2026-DEV-053/BerlinClock.git

2. Once the Gradle sync is successful, proceed to run the app on either an emulator or a connected device. The minimum API level required is 24, and it has been tested against a device running Android 17.

## Tech Stack

- Kotlin
- Jetpack Compose
- Coroutines + Flow for asynchronous
- MVVM + Clean architecture + TDD
- StateFlow and ViewModel
- Hilt Dependency Injection
- Unit tests - JUnit, MockK, Turbine
