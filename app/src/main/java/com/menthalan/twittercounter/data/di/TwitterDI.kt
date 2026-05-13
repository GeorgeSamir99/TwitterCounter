package com.menthalan.twittercounter.data.di

import com.menthalan.twittercounter.data.repo.TwitterRepoImpl
import com.menthalan.twittercounter.domain.repo.TwitterRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class TwitterDI{
    @Binds
    @Singleton
    abstract fun provideTwitterRepository(twitterRepoImpl: TwitterRepoImpl): TwitterRepo
}