package com.prashant.naik.ezcart.di

import android.content.Context
import androidx.room.Room
import com.prashant.naik.ezcart.data.item.ItemsDao
import com.prashant.naik.ezcart.data.item.ItemsDatabase
import com.prashant.naik.ezcart.data.profile.UserProfileDao
import com.prashant.naik.ezcart.data.profile.UserProfileDatabase
import com.prashant.naik.ezcart.utils.Constants.Companion.ITEMS_DATABASE
import com.prashant.naik.ezcart.utils.Constants.Companion.USER_PROFILE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideUserProfileDatabase(@ApplicationContext context: Context): UserProfileDatabase {
        return Room.databaseBuilder(context, UserProfileDatabase::class.java, USER_PROFILE_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideItemsDatabase(@ApplicationContext context: Context): ItemsDatabase {
        return Room.databaseBuilder(context, ItemsDatabase::class.java, ITEMS_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesUserProfileDao(database: UserProfileDatabase): UserProfileDao = database.userProfileDao()

    @Singleton
    @Provides
    fun providesItemsDao(database: ItemsDatabase): ItemsDao = database.itemsDao()
}