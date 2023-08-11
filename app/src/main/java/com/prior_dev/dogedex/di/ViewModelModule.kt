package com.prior_dev.dogedex.di

import android.content.Context
import com.prior_dev.dogedex.LABEL_PATH
import com.prior_dev.dogedex.MODEL_PATH
import com.prior_dev.dogedex.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import org.tensorflow.lite.support.common.FileUtil
import retrofit2.Retrofit
import java.nio.MappedByteBuffer

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @ViewModelScoped
    @Provides
    fun providesApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @ViewModelScoped
    @Provides
    fun providesClassifierModel(@ApplicationContext context: Context): MappedByteBuffer =
        FileUtil.loadMappedFile(context, MODEL_PATH)

    @ViewModelScoped
    @Provides
    fun providesClassifierLabels(@ApplicationContext context: Context): MutableList<String> =
        FileUtil.loadLabels(context, LABEL_PATH)


}