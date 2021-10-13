package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.item.ItemsDao
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.data.profile.UserProfileDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val itemsDao: ItemsDao
): LocalDataSource {
    override suspend fun registerProfile(userProfile: UserProfile) {
        userProfileDao.addUserProfile(userProfile)
    }

    override suspend fun loginUserProfile(userId: String, password: String): UserProfile {
        return userProfileDao.loginUser(userId, password)
    }

    override suspend fun addToCart(item: Item) {
        return itemsDao.addItemToCart(item)
    }

    override suspend fun getCartItems(): List<Item> {
        return itemsDao.getCartItems()
    }

    override suspend fun removeCartItem(itemName: String) {
        itemsDao.removeItemFromCart(itemName)
    }
}