package com.prior_dev.dogedex.di

import com.prior_dev.dogedex.auth.AuthRepository
import com.prior_dev.dogedex.auth.AuthRepositoryTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class AuthModule {
    @ViewModelScoped
    @Binds
    abstract fun providesAuthRepository(authRepository: AuthRepository): AuthRepositoryTask
}