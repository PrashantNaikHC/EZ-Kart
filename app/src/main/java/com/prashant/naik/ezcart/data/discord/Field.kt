package com.prashant.naik.ezcart.data.discord

import com.google.gson.annotations.SerializedName

data class Field(
    private val name: String,
    private val value: String,
    @SerializedName("inline")
    private val isInline: Boolean
)