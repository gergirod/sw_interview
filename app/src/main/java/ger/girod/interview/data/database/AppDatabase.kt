package ger.girod.interview.data.database

import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import ger.girod.interview.data.dao.UserDao
import ger.girod.interview.domain.model.UserModel
import org.koin.dsl.module

val databaseModule = module {
    single { databaseBuilder(get(), AppDatabase::class.java, "users_database").build() }
    single { get<AppDatabase>().userDao() }
}

@Database(entities = [UserModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
}