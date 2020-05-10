package ger.girod.interview.domain.use_cases

import ger.girod.interview.data.repository.UsersRepository
import ger.girod.interview.domain.response.ResultWrapper
import ger.girod.interview.domain.response.UserListResponse

class GetUsersUseCaseImpl(private val repository: UsersRepository) : GetUsersUseCase {

    override suspend fun getUserList(page: Int, results: Int): ResultWrapper<UserListResponse> {
       return repository.getUserList(page, results)
    }
}