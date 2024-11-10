package com.example.internproject.di

import com.example.internproject.data.repository.UserRepositoryImpl
import com.example.internproject.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class BindModule {
    @Binds
    @ViewModelScoped
    abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository
}