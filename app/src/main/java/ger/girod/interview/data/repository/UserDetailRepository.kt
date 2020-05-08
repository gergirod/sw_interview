package ger.girod.interview.data.repository

import ger.girod.interview.data.api.executeLocalRequest
import ger.girod.interview.data.dao.UserDao
import ger.girod.interview.domain.model.UserModel
import ger.girod.interview.domain.response.ResultWrapper
import ger.girod.interview.domain.use_cases.SaveFavoriteUserUseCase
import kotlinx.coroutines.Dispatchers

class UserDetailRepository(private val dao: UserDao) : SaveFavoriteUserUseCase {

    override suspend fun saveFavoriteUser(userModel: UserModel) : ResultWrapper<Long>{
        return executeLocalRequest(Dispatchers.IO) {
            dao.saveUser(userModel)
        }
    }
}