package com.prashant.naik.ezcart.network

import android.graphics.Color
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Constructs a new DiscordWebhook instance
 *
 * @param url The webhook URL obtained in Discord
 */
class DiscordWebhook(
    private val url: String,
    private var content: String? = null,
    private var username: String? = null,
    private var avatarUrl: String? = null,
    private var tts: Boolean = false,
    private val embeds: MutableList<EmbedObject> = ArrayList()
) {

    fun addEmbed(embed: EmbedObject) {
        embeds.add(embed)
    }

    @Throws(IOException::class)
    fun execute() {
        if (content == null && embeds.isEmpty()) {
            throw IllegalArgumentException("Set content or add at least one EmbedObject")
        }
        val json = JSONObject()
        json.put("content", content)
        json.put("username", username)
        json.put("avatar_url", avatarUrl)
        json.put("tts", tts)
        if (embeds.isNotEmpty()) {
            val embedObjects: MutableList<JSONObject> = ArrayList()
            for (embed: EmbedObject in embeds) {
                val jsonEmbed: JSONObject = JSONObject()
                jsonEmbed.put("title", embed.title)
                jsonEmbed.put("description", embed.description)
                jsonEmbed.put("url", embed.url)
                if (embed.getColor() != null) {
                    val color: Color? = embed.getColor()
                    var rgb: Int = color.getRed()
                    rgb = (rgb shl 8) + color.getGreen()
                    rgb = (rgb shl 8) + color.getBlue()
                    jsonEmbed.put("color", rgb)
                }
                val footer: Footer? = embed.footer
                val image = embed.image
                val thumbnail: Thumbnail? = embed.thumbnail
                val author: Author? = embed.author
                val fields = embed.getFields()
                if (footer != null) {
                    val jsonFooter: JSONObject = JSONObject()
                    jsonFooter.put("text", footer.getText())
                    jsonFooter.put("icon_url", footer.getIconUrl())
                    jsonEmbed.put("footer", jsonFooter)
                }
                if (image != null) {
                    val jsonImage: JSONObject = JSONObject()
                    jsonImage.put("url", image.getUrl())
                    jsonEmbed.put("image", jsonImage)
                }
                if (thumbnail != null) {
                    val jsonThumbnail: JSONObject = JSONObject()
                    jsonThumbnail.put("url", thumbnail.getUrl())
                    jsonEmbed.put("thumbnail", jsonThumbnail)
                }
                if (author != null) {
                    val jsonAuthor: JSONObject = JSONObject()
                    jsonAuthor.put("name", author.getName())
                    jsonAuthor.put("url", author.getUrl())
                    jsonAuthor.put("icon_url", author.getIconUrl())
                    jsonEmbed.put("author", jsonAuthor)
                }
                val jsonFields: MutableList<JSONObject> = ArrayList()
                for (field: EmbedObject.Field in fields) {
                    val jsonField: JSONObject = JSONObject()
                    jsonField.put("name", field.getName())
                    jsonField.put("value", field.getValue())
                    jsonField.put("inline", field.isInline())
                    jsonFields.add(jsonField)
                }
                jsonEmbed.put("fields", jsonFields.toTypedArray())
                embedObjects.add(jsonEmbed)
            }
            json.put("embeds", embedObjects.toTypedArray())
        }
        val url = URL(url)
        val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
        connection.addRequestProperty("Content-Type", "application/json")
        connection.addRequestProperty("User-Agent", "Java-DiscordWebhook-BY-Gelox_")
        connection.doOutput = true
        connection.requestMethod = "POST"
        val stream: OutputStream = connection.getOutputStream()
        stream.write(json.toString().toByteArray())
        stream.flush()
        stream.close()
        connection.inputStream
            .close() //I'm not sure why but it doesn't work without getting the InputStream
        connection.disconnect()
    }

    class EmbedObject() {
        var title: String? = null
            private set
        var description: String? = null
            private set
        var url: String? = null
            private set
        private var color: Color? = null
        var footer: Footer? = null
            private set
        var thumbnail: Thumbnail? = null
            private set
        var image: Image? = null
            private set
        var author: Author? = null
            private set
        private val fields: MutableList<Field> = ArrayList()
        fun getColor(): Color? {
            return color
        }

        fun getFields(): List<Field> {
            return fields
        }

        fun setTitle(title: String?): EmbedObject {
            this.title = title
            return this
        }

        fun setDescription(description: String?): EmbedObject {
            this.description = description
            return this
        }

        fun setUrl(url: String?): EmbedObject {
            this.url = url
            return this
        }

        fun setColor(color: Color?): EmbedObject {
            this.color = color
            return this
        }

        fun setFooter(text: String, icon: String): EmbedObject {
            footer = Footer(text, icon)
            return this
        }

        fun setThumbnail(url: String): EmbedObject {
            thumbnail = Thumbnail(url)
            return this
        }

        fun setImage(url: String?): EmbedObject {
            image = Image(url)
            return this
        }

        fun setAuthor(name: String, url: String, icon: String): EmbedObject {
            author = Author(name, url, icon)
            return this
        }

        fun addField(name: String?, value: String?, inline: Boolean): EmbedObject {
            fields.add(Field(name, value, inline))
            return this
        }

        private inner class Footer private constructor(
            private val text: String,
            private val iconUrl: String
        )

        private inner class Thumbnail private constructor(private val url: String)
        private inner class Image private constructor(private val url: String)
        private inner class Author private constructor(
            private val name: String,
            private val url: String,
            private val iconUrl: String
        )

        private inner class Field private constructor(
            private val name: String,
            private val value: String,
            private val isInline: Boolean
        )
    }

    private inner class JSONObject() {
        private val map: HashMap<String, Any> = HashMap()
        fun put(key: String, value: Any?) {
            if (value != null) {
                map[key] = value
            }
        }

        override fun toString(): String {
            val builder = StringBuilder()
            val entrySet: Set<Map.Entry<String, Any>> = map.entrySet()
            builder.append("{")
            var i = 0
            for (entry: Map.Entry<String, Any> in entrySet) {
                builder.append(quote(key)).append(":")
                if (`val` is String) {
                    builder.append(quote(`val`.toString()))
                } else if (`val` is Int) {
                    builder.append(Integer.valueOf(`val`.toString()))
                } else if (`val` is Boolean) {
                    builder.append(`val`)
                } else if (`val` is JSONObject) {
                    builder.append(`val`.toString())
                } else if (`val`.javaClass.isArray) {
                    builder.append("[")
                    val len: Int = Array.getLength(`val`)
                    for (j in 0 until len) {
                        builder.append(Array.get(`val`, j).toString())
                            .append(if (j != len - 1) "," else "")
                    }
                    builder.append("]")
                }
                builder.append(if (++i == entrySet.size) "}" else ",")
            }
            return builder.toString()
        }

        private fun quote(string: String): String {
            return "\"" + string + "\""
        }
    }
}