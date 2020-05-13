package ger.girod.interview.data.repository

import ger.girod.interview.data.api.UsersApi
import ger.girod.interview.data.api.executeLocalRequest
import ger.girod.interview.data.api.executeNetworkRequest
import ger.girod.interview.data.dao.UserDao
import ger.girod.interview.data.utils.NetworkHandler
import ger.girod.interview.domain.model.UserModel
import ger.girod.interview.domain.response.ResultWrapper
import ger.girod.interview.domain.response.UserListResponse
import ger.girod.interview.domain.use_cases.GetFavoritesUserUseCase
import ger.girod.interview.domain.use_cases.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject

class UsersRepository(private val usersApi: UsersApi, private val userDao: UserDao) : KoinComponent {

    private val networkHandler : NetworkHandler by inject()

    suspend fun getFavoritesUsers(): ResultWrapper<List<UserModel>> {
        return  executeLocalRequest(Dispatchers.IO) {
           userDao.getAllFavoritesUsers()
        }
    }

    suspend fun getUserList(page: Int, results: Int): ResultWrapper<UserListResponse> {
        return executeNetworkRequest(Dispatchers.IO, networkHandler) {
            usersApi.getUsers(page, results)
        }
    }
}