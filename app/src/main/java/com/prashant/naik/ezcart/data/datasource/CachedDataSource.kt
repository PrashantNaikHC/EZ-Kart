package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.Item

interface CachedDataSource {

    fun loadLoginItems(): List<Item>
    fun saveLoginItems(itemsList: List<Item>)
}