package com.kata.berlinclock

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context
    ): Application {
        // Force the test runner to load HiltTestApplication
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}