package ger.girod.interview.domain.model

import androidx.room.Entity

@Entity
data class PictureModel(
    val large : String,
    val medium : String,
    val thumbnail : String
)