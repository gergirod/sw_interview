package ger.girod.interview.domain.response

import ger.girod.interview.domain.model.InfoModel
import ger.girod.interview.domain.model.UserModel

data class UserListResponse(
    val results : List<UserModel>,
    val info : InfoModel
)