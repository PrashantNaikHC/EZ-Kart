package com.prashant.naik.ezcart.domain

import com.prashant.naik.ezcart.data.datasource.CachedDataSource
import com.prashant.naik.ezcart.data.datasource.LocalDataSource
import com.prashant.naik.ezcart.data.datasource.RemoteDataSource
import com.prashant.naik.ezcart.data.profile.UserProfile
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
    val cachedDataSource: CachedDataSource
): Repository {
    override suspend fun registerUser(userProfile: UserProfile) {
        localDataSource.registerProfile(userProfile)
    }

    override suspend fun loginUser(userId: String, password: String): UserProfile {
        return localDataSource.loginUserProfile(userId, password)
    }
}