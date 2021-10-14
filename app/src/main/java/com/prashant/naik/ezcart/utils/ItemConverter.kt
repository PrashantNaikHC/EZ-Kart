package com.prashant.naik.ezcart.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prashant.naik.ezcart.data.Item
import java.lang.reflect.Type


class ItemConverter {

    @TypeConverter
    fun fromItemList(item: List<Item?>?): String? {
        if (item == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Item?>?>() {}.type
        return gson.toJson(item, type)
    }

    @TypeConverter
    fun toItemList(itemString: String?): List<Item>? {
        if (itemString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Item?>?>() {}.type
        return gson.fromJson<List<Item>>(itemString, type)
    }
}