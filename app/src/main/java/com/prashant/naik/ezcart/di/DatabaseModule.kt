package com.prashant.naik.ezcart.di

import android.content.Context
import androidx.room.Room
import com.prashant.naik.ezcart.data.item.CartItemsDao
import com.prashant.naik.ezcart.data.item.CartItemsDatabase
import com.prashant.naik.ezcart.data.item.LoginItemDatabase
import com.prashant.naik.ezcart.data.item.LoginItemsDao
import com.prashant.naik.ezcart.data.profile.UserProfileDao
import com.prashant.naik.ezcart.data.profile.UserProfileDatabase
import com.prashant.naik.ezcart.utils.Constants.Companion.ITEMS_DATABASE
import com.prashant.naik.ezcart.utils.Constants.Companion.LOGIN_ITEMS_DATABASE
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
    fun provideItemsDatabase(@ApplicationContext context: Context): CartItemsDatabase {
        return Room.databaseBuilder(context, CartItemsDatabase::class.java, ITEMS_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginItemsDatabase(@ApplicationContext context: Context): LoginItemDatabase {
        return Room.databaseBuilder(context, LoginItemDatabase::class.java, LOGIN_ITEMS_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesUserProfileDao(database: UserProfileDatabase): UserProfileDao = database.userProfileDao()

    @Singleton
    @Provides
    fun providesItemsDao(databaseCartCart: CartItemsDatabase): CartItemsDao = databaseCartCart.cartItemsDao()

    @Singleton
    @Provides
    fun providesLoginItemsDao(loginItemDatabase: LoginItemDatabase): LoginItemsDao = loginItemDatabase.loginItemsDao()
}