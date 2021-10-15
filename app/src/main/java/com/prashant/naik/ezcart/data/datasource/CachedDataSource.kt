package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order

interface CachedDataSource {

    fun loadLoginItems(): List<Item>
    fun saveLoginItems(itemsList: List<Item>)

    fun saveOrders(ordersList: List<Order>)
    fun loadOrders(): List<Order>

    fun clearUserData()
}