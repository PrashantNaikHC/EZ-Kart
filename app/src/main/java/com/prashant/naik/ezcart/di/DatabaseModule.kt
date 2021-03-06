package com.prashant.naik.ezcart.di

import android.content.Context
import androidx.room.Room
import com.prashant.naik.ezcart.data.feedback.FeedbackDao
import com.prashant.naik.ezcart.data.feedback.FeedbackDatabase
import com.prashant.naik.ezcart.data.item.*
import com.prashant.naik.ezcart.data.profile.UserProfileDao
import com.prashant.naik.ezcart.data.profile.UserProfileDatabase
import com.prashant.naik.ezcart.utils.Constants.Companion.FEEDBACK_DATABASE
import com.prashant.naik.ezcart.utils.Constants.Companion.ITEMS_DATABASE
import com.prashant.naik.ezcart.utils.Constants.Companion.LOGIN_ITEMS_DATABASE
import com.prashant.naik.ezcart.utils.Constants.Companion.ORDER_ITEMS_DATABASE
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
    fun provideOrdersDatabase(@ApplicationContext context: Context): OrderDatabase {
        return Room.databaseBuilder(context, OrderDatabase::class.java, ORDER_ITEMS_DATABASE)
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
    fun provideFeedbackDatabase(@ApplicationContext context: Context): FeedbackDatabase {
        return Room.databaseBuilder(context, FeedbackDatabase::class.java, FEEDBACK_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesUserProfileDao(database: UserProfileDatabase): UserProfileDao = database.userProfileDao()

    @Singleton
    @Provides
    fun providesOrderItemsDao(database: OrderDatabase): OrdersDao = database.orderItemsDao()

    @Singleton
    @Provides
    fun providesItemsDao(databaseCartCart: CartItemsDatabase): CartItemsDao = databaseCartCart.cartItemsDao()

    @Singleton
    @Provides
    fun providesLoginItemsDao(loginItemDatabase: LoginItemDatabase): LoginItemsDao = loginItemDatabase.loginItemsDao()

    @Singleton
    @Provides
    fun providesFeedbackDao(feedbackDatabase: FeedbackDatabase): FeedbackDao = feedbackDatabase.feedbackDao()
}