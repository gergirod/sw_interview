package ger.girod.interview.domain.use_cases

import ger.girod.interview.domain.model.UserModel
import ger.girod.interview.domain.response.ResultWrapper

interface SaveFavoriteUserUseCase {

    suspend fun saveFavoriteUser(userModel: UserModel) : ResultWrapper<Long>

}