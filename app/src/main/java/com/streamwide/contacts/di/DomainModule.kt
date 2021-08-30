package com.streamwide.contacts.di

import com.streamwide.contacts.data.repository.ContactRepository
import com.streamwide.contacts.data.repository.ContactRepositoryImpl
import com.streamwide.contacts.utils.DefaultErrorFactory
import com.streamwide.contacts.utils.ErrorFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    internal abstract fun provideContactRepository(contactRepository: ContactRepositoryImpl): ContactRepository

    @Binds
    internal abstract fun provideErrorFactory(errorFactory: DefaultErrorFactory): ErrorFactory
}