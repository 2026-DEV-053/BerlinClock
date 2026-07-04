package com.kata.berlinclock.di

import com.kata.berlinclock.data.datasource.SystemTimeDataSource
import com.kata.berlinclock.data.repository.TimeRepositoryImpl
import com.kata.berlinclock.domain.repository.TimeRepository
import com.kata.berlinclock.domain.usecase.ConvertTimeToBerlinClockUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSystemTimeDataSource(): SystemTimeDataSource {
        return SystemTimeDataSource()
    }

    @Singleton
    @Provides
    fun provideTimeRepository(
        dataSource: SystemTimeDataSource
    ): TimeRepository {
        return TimeRepositoryImpl(dataSource)
    }

    @Singleton
    @Provides
    fun provideConvertTimeToBerlinClockUseCase(
        timeRepository: TimeRepository
    ): ConvertTimeToBerlinClockUseCase {
        return ConvertTimeToBerlinClockUseCase(timeRepository)
    }
}