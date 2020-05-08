package ger.girod.interview.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModelView(val gender : String,
                         val nameModelView : NameModelView,
                         val email : String,
                         val phone : String,
                         val cell : String,
                         val nat : String,
                         val picture : PictureModelView) : Parcelable {

    fun toUserModel() : UserModel {
        return UserModel(0, gender, NameModel(nameModelView.title, nameModelView.first, nameModelView.last),
        email, phone, cell, nat, PictureModel(picture.large, picture.medium, picture.thumbnail))

    }

}