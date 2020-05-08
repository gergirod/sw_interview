package ger.girod.interview.domain.model

import androidx.room.Entity

@Entity
data class NameModel(
    val title : String,
    val first : String,
    val last : String
) {
    fun getCompleteName() : String {

        return "$title $first $last"

    }
}