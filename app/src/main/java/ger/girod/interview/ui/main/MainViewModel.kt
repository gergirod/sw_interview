package ger.girod.interview.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.interview.data.repository.UsersRepository
import ger.girod.interview.domain.model.NameModelView
import ger.girod.interview.domain.model.PictureModelView
import ger.girod.interview.domain.model.UserModel
import ger.girod.interview.domain.model.UserModelView
import ger.girod.interview.domain.response.ResultWrapper
import ger.girod.interview.ui.utils.ScreenState
import kotlinx.coroutines.launch

class MainViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    var userList : ArrayList<UserModelView> = ArrayList()
    var userInitialData : MutableLiveData<List<UserModelView>> = MutableLiveData()
    var userLoadMoreData : MutableLiveData<List<UserModelView>> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()
    var favoritesUserListData : MutableLiveData<List<UserModelView>> = MutableLiveData()
    var screenStateData : MutableLiveData<ScreenState> = MutableLiveData()

    fun getInitialUserList() {
        viewModelScope.launch {
            screenStateData.value = ScreenState.Loading
            when(val response = usersRepository.getUserList(1, 50)) {
                is ResultWrapper.Success -> {

                    userList.addAll(response.value.results.map { mapUserModel(it) })
                    userInitialData.value = response.value.results.map {mapUserModel(it)}
                }
                is ResultWrapper.GenericError -> errorData.postValue(response.errorMessage)
                is ResultWrapper.NetworkError -> errorData.postValue(response.errorMessage)
            }
            screenStateData.value = ScreenState.LoadingFinish
        }
    }

    private fun mapUserModel(userModel: UserModel) : UserModelView {

        return UserModelView(userModel.gender, NameModelView(userModel.name.title, userModel.name.first,
            userModel.name.last), userModel.email, userModel.phone, userModel.cell, userModel.nat,
            PictureModelView(userModel.picture.large, userModel.picture.medium, userModel.picture.thumbnail)
        )

    }

    fun getLoadMoreList(page : Int) {
        viewModelScope.launch {
            when(val response = usersRepository.getUserList(page, 50)) {
                is ResultWrapper.Success -> {
                    userList.addAll(response.value.results.map { mapUserModel(it)})
                    userInitialData.value = response.value.results.map {mapUserModel(it)}
                }
                is ResultWrapper.GenericError -> errorData.postValue(response.errorMessage)
                is ResultWrapper.NetworkError -> errorData.postValue(response.errorMessage)
            }
        }
    }

    fun getFavoritesUserList() {
        viewModelScope.launch {
            when(val response = usersRepository.getFavoritesUsers()) {
                is ResultWrapper.Success -> {
                    favoritesUserListData.value = response.value.map { mapUserModel(it) }
                }
                is ResultWrapper.NetworkError -> errorData.postValue(response.errorMessage)
                is ResultWrapper.GenericError -> errorData.postValue(response.errorMessage)
            }
        }
    }

    fun searchUsers(searchText : String) : List<UserModelView> {

        return userList.filter { userModel -> userModel.nameModelView.getCompleteName().toLowerCase().contains(searchText.toLowerCase()) }

    }

}
