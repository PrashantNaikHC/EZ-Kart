package com.prashant.naik.ezcart.data.discord

import com.google.gson.annotations.SerializedName

data class DiscordObject(
    var tts: Boolean = false,
    var content: String,
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val embeds: MutableList<EmbedObject> = ArrayList()
)