package com.prior_dev.dogedex.di

import com.prior_dev.dogedex.auth.AuthRepository
import com.prior_dev.dogedex.auth.AuthRepositoryTask
import com.prior_dev.dogedex.doglist.DogRepository
import com.prior_dev.dogedex.doglist.DogRepositoryTask
import com.prior_dev.dogedex.machinglearning.ClassifierRepository
import com.prior_dev.dogedex.machinglearning.ClassifierRepositoryTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class DogSelftModule {
    @ViewModelScoped
    @Binds
    abstract fun provideDogRepository(dogRepository: DogRepository): DogRepositoryTask

    @ViewModelScoped
    @Binds
    abstract fun providesAuthRepository(authRepository: AuthRepository): AuthRepositoryTask

    @ViewModelScoped
    @Binds
    abstract fun providesClassifier(classifierRepository: ClassifierRepository): ClassifierRepositoryTask

}
