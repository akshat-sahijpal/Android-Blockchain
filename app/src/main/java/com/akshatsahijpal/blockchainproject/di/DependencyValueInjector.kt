package com.akshatsahijpal.blockchainproject.di

import android.content.Context
import com.akshatsahijpal.blockchainproject.repository.HomeRepository.HomeScreenRepository
import com.akshatsahijpal.blockchainproject.repository.PostCreatorRepository.PostCreatorRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyValueInjector {
    @Singleton
    @Provides
    fun providePostCreatorRepo(@ApplicationContext cont: Context) = PostCreatorRepo(cont)


    @Singleton
    @Provides
    fun provideHomeScreenRepository() = HomeScreenRepository()
}