package ger.girod.interview.domain.use_cases

import ger.girod.interview.data.repository.UserDetailRepository
import ger.girod.interview.domain.model.UserModel
import ger.girod.interview.domain.response.ResultWrapper

class SaveFavoriteUsersUseCaseImpl( private val userDetailRepository: UserDetailRepository) : SaveFavoriteUserUseCase {

    override suspend fun saveFavoriteUser(userModel: UserModel): ResultWrapper<Long> {
        return userDetailRepository.saveFavoriteUser(userModel)
    }
}