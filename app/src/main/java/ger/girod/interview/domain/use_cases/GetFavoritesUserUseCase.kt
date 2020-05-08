package ger.girod.interview.domain.use_cases

import ger.girod.interview.domain.response.ResultWrapper
import ger.girod.interview.domain.model.UserModel

interface GetFavoritesUserUseCase {

    suspend fun getFavoritesUsers() : ResultWrapper<List<UserModel>>
}