package com.prashant.naik.ezcart.utils

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.item.LoginItem
import java.security.MessageDigest

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

fun String.toSHA256(): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
}