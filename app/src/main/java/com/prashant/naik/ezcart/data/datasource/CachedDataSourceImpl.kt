package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.Item
import javax.inject.Inject

class CachedDataSourceImpl @Inject constructor(): CachedDataSource {
    private var loginItems = listOf<Item>()

    override fun loadLoginItems(): List<Item> {
        return loginItems
    }

    override fun saveLoginItems(itemsList: List<Item>) {
        this.loginItems = itemsList
    }
}