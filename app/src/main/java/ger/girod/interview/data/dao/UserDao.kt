package ger.girod.interview.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ger.girod.interview.domain.model.UserModel

@Dao
interface UserDao {

    @Insert
    suspend fun saveUser(user : UserModel) : Long

    @Query("Select * FROM user_table")
    suspend fun getAllFavoritesUsers() : List<UserModel>

}