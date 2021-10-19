package com.prashant.naik.ezcart.di

import android.content.Context
import androidx.room.Room
import com.prashant.naik.ezcart.data.feedback.FeedbackDatabase
import com.prashant.naik.ezcart.data.item.CartItemsDatabase
import com.prashant.naik.ezcart.data.item.LoginItemDatabase
import com.prashant.naik.ezcart.data.item.OrderDatabase
import com.prashant.naik.ezcart.data.profile.UserProfileDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideUSerProfileInMemoryRoomDb(@ApplicationContext applicationContext: Context) : UserProfileDatabase {
        return Room.inMemoryDatabaseBuilder(applicationContext, UserProfileDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    @Provides
    @Named("test_db")
    fun provideFeedbackInMemoryRoomDb(@ApplicationContext applicationContext: Context) : FeedbackDatabase {
        return Room.inMemoryDatabaseBuilder(applicationContext, FeedbackDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    @Provides
    @Named("test_db")
    fun provideCartItemsInMemoryRoomDb(@ApplicationContext applicationContext: Context) : CartItemsDatabase {
        return Room.inMemoryDatabaseBuilder(applicationContext, CartItemsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    @Provides
    @Named("test_db")
    fun provideLoginItemsInMemoryRoomDb(@ApplicationContext applicationContext: Context) : LoginItemDatabase {
        return Room.inMemoryDatabaseBuilder(applicationContext, LoginItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Named("test_db")
    fun provideOrdersInMemoryRoomDb(@ApplicationContext applicationContext: Context) : OrderDatabase {
        return Room.inMemoryDatabaseBuilder(applicationContext, OrderDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}