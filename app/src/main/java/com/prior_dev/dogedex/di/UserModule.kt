package com.prior_dev.dogedex.di

import com.prior_dev.dogedex.auth.UserRepository
import com.prior_dev.dogedex.auth.UserRepositoryTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Singleton
    @Binds
    abstract fun providesUserRepo(userRepository: UserRepository): UserRepositoryTask
}