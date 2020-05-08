package ger.girod.interview.domain.use_cases

import ger.girod.interview.domain.response.ResultWrapper
import ger.girod.interview.domain.response.UserListResponse

interface GetUsersUseCase {

    suspend fun getUserList(page : Int, results: Int) : ResultWrapper<UserListResponse>

}