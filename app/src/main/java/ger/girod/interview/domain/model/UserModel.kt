package ger.girod.interview.domain.model

import androidx.room.*


@Entity(tableName = "user_table")
data class UserModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") val userId: Long,
    val gender : String,
    @Embedded val name : NameModel,
    val email : String,
    val phone : String,
    val cell : String,
    val nat : String,
    @Embedded val picture : PictureModel
)