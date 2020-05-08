package ger.girod.interview.data.api


import ger.girod.interview.data.utils.NetworkHandler
import ger.girod.interview.domain.response.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> executeNetworkRequest(dispatcher: CoroutineDispatcher, networkHandler: NetworkHandler, request: suspend () -> T) :ResultWrapper<T> {
    return withContext(dispatcher) {
        if(networkHandler.isInternetAvailable()) {
            try {
                ResultWrapper.Success(request.invoke())
            }catch (e : Exception) {
                ResultWrapper.GenericError(e.message)
            }
        }else {
            ResultWrapper.NetworkError("no internet connection")
        }
    }
}

suspend fun <T> executeLocalRequest(dispatcher: CoroutineDispatcher, request: suspend () -> T) :ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(request.invoke())
        } catch (e: Exception) {
            ResultWrapper.GenericError(e.message)
        }
    }
}

