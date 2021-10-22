package com.prashant.naik.ezcart.data.discord

import com.google.gson.annotations.SerializedName

data class Author(
    private val name: String,
    private val url: String,
    @SerializedName("icon_url")
    private val iconUrl: String
)