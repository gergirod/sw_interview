package ger.girod.interview.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PictureModelView(
    val large : String,
    val medium : String,
    val thumbnail : String
) : Parcelable