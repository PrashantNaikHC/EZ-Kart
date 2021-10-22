package com.prashant.naik.ezcart.data.discord

import android.graphics.Color
import android.media.Image

data class EmbedObject(
    private var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var color: Color? = null,
    var footer: Footer? = null,
    var thumbnail: Thumbnail? = null,
    var image: Image? = null,
    var author: Author? = null,
    private val fields: MutableList<Field> = ArrayList()
)