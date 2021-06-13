package com.amityadav.kreditbeeandroidsample.presentation.album.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amityadav.kreditbeeandroidsample.data.network.NetworkManagerImpl
import com.amityadav.kreditbeeandroidsample.data.preference.PreferenceHelperImp
import com.amityadav.kreditbeeandroidsample.domain.base.ApiResponse
import com.amityadav.kreditbeeandroidsample.domain.model.api.ApiError
import com.amityadav.kreditbeeandroidsample.domain.model.api.Status
import com.amityadav.kreditbeeandroidsample.domain.model.api.ViewModelResponse
import com.amityadav.kreditbeeandroidsample.presentation.base.BaseViewModel
import kotlinx.coroutines.cancel

class AlbumViewModel constructor(private val networkManagerImpl: NetworkManagerImpl,
                                 private val preferenceHelperImp: PreferenceHelperImp) : BaseViewModel(){



    private val viewModelResponseMutableLiveData: MutableLiveData<ViewModelResponse> = MutableLiveData<ViewModelResponse>()

    init {
        fetchAlbumList()
    }

    private fun fetchAlbumList() {
        networkManagerImpl.getAlbums(viewModelScope, null, object : ApiResponse<Any> {
            override fun onSuccess(result: Any) {
                val response = result as List<*>
                if (!response.isNullOrEmpty()){
                    viewModelResponseMutableLiveData.value = ViewModelResponse(Status.SUCCESS, response, null)
                }else{
                    viewModelResponseMutableLiveData.value = ViewModelResponse.defaultError("Oops. Try again !")
                }
            }
            override fun onError(apiError: ApiError?) {
                viewModelResponseMutableLiveData.value = ViewModelResponse.defaultError(apiError?.message.orEmpty())
            }
        },)
    }

    fun getAlbumListData(): MutableLiveData<ViewModelResponse> {
        return viewModelResponseMutableLiveData
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}