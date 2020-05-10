package ger.girod.interview.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.interview.domain.model.UserModelView
import ger.girod.interview.domain.response.ResultWrapper
import ger.girod.interview.domain.use_cases.SaveFavoriteUserUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val saveFavoriteUserUseCase: SaveFavoriteUserUseCase) : ViewModel() {

    var saveUserData : MutableLiveData<Long> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()

    fun saveUserToFavorites(userModelView: UserModelView) {
        viewModelScope.launch {
            when(val response = saveFavoriteUserUseCase.saveFavoriteUser(userModelView.toUserModel())) {

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