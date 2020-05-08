package ger.girod.interview.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NameModelView(
    val title : String,
    val first : String,
    val last : String
) : Parcelable {
    fun getCompleteName() : String {

        return "$title $first $last"

    }
}