package com.prashant.naik.ezcart.di

import com.prashant.naik.ezcart.data.datasource.*
import com.prashant.naik.ezcart.domain.Repository
import com.prashant.naik.ezcart.domain.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsLocalDataSource(localDataSourceImpl: LocalDataSourceImpl) : LocalDataSource

    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl) : RemoteDataSource

    @Binds
    abstract fun bindsCachedDataSource(cachedDataSourceImpl: CachedDataSourceImpl) : CachedDataSource

    @Binds
    abstract fun bindsRepository(repositoryImpl: RepositoryImpl): Repository
}