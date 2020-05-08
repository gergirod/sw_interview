package ger.girod.interview.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.interview.data.repository.UserDetailRepository
import ger.girod.interview.domain.model.UserModelView
import ger.girod.interview.domain.response.ResultWrapper
import kotlinx.coroutines.launch

class DetailViewModel(private val userDetailRepository: UserDetailRepository) : ViewModel() {

    var saveUserData : MutableLiveData<Long> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()

    fun saveUserToFavorites(userModelView: UserModelView) {
        viewModelScope.launch {
            when(val response = userDetailRepository.saveFavoriteUser(userModelView.toUserModel())) {

                is ResultWrapper.Success -> {
                    saveUserData.value = response.value
                }
                is ResultWrapper.GenericError -> {
                    errorData.value = response.errorMessage
                }

            }
        }
    }

}