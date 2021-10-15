package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import javax.inject.Inject

class CachedDataSourceImpl @Inject constructor(): CachedDataSource {
    private var loginItems = listOf<Item>()
    private var orders = listOf<Order>()

    override fun loadLoginItems(): List<Item> {
        return loginItems
    }

    override fun saveLoginItems(itemsList: List<Item>) {
        this.loginItems = itemsList
    }

    override fun saveOrders(ordersList: List<Order>) {
        orders = ordersList
    }

    override fun loadOrders(): List<Order> {
        return this.orders
    }

    override fun clearUserData() {
        loginItems = listOf()
        orders = listOf()
    }
}