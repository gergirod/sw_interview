package ger.girod.interview.data.api

import ger.girod.interview.domain.response.UserListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET(BASE_URL)
    suspend fun getUsers(@Query("page") page : Int, @Query("results") results : Int) : UserListResponse

}