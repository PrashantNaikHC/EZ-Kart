package com.prashant.naik.ezcart.data.discord

import com.google.gson.annotations.SerializedName

data class Footer(
    private val text: String,
    @SerializedName("icon_url")
    private val iconUrl: String
)