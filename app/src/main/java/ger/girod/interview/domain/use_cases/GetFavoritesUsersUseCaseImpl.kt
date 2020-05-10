package ger.girod.interview.domain.use_cases

import ger.girod.interview.data.repository.UsersRepository
import ger.girod.interview.domain.model.UserModel
import ger.girod.interview.domain.response.ResultWrapper

class GetFavoritesUsersUseCaseImpl(private val repository: UsersRepository) : GetFavoritesUserUseCase {
    override suspend fun getFavoritesUsers(): ResultWrapper<List<UserModel>> {
        return repository.getFavoritesUsers()
    }
}