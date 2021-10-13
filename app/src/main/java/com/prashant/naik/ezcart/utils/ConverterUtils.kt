package com.prashant.naik.ezcart.utils

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.item.LoginItem

fun List<LoginItem>.toListOfItems(): List<Item> {
    return this.map { it.toItem() }
}

fun List<Item>.toListOfLoginItems(): List<LoginItem> {
    return this.map { it.toLoginItem() }
}

fun LoginItem.toItem(): Item {
    return Item(
        currency, desc, expiryDate, itemName, price, quantity
    )
}

fun Item.toLoginItem(): LoginItem {
    return LoginItem(
        currency, desc, expiryDate, itemName, price, quantity
    )
}