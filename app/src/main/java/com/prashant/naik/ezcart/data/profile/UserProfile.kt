package com.prashant.naik.ezcart.data.profile

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prashant.naik.ezcart.utils.Constants.Companion.USER_PROFILE_DATABASE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = USER_PROFILE_DATABASE)
data class UserProfile (
    @PrimaryKey
    val userId: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val phone: String
) : Parcelable {

    fun getNormalisedName(): String {
        return "$firstName $lastName"
    }
}