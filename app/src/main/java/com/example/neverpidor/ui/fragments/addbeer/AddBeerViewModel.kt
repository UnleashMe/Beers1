package com.example.neverpidor.ui.fragments.addbeer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neverpidor.data.MenuItemsRepository
import com.example.neverpidor.model.beer.BeerPostResponse
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackDeleteResponse
import com.example.neverpidor.model.snack.SnackRequest
import kotlinx.coroutines.launch
import retrofit2.Response

class AddBeerViewModel: ViewModel() {

    private val repository = MenuItemsRepository()


    private val _beerResponse = MutableLiveData<Response<BeerPostResponse>>()
    val beerResponse: LiveData<Response<BeerPostResponse>> = _beerResponse

    private val _snackResponse = MutableLiveData<Response<SnackDeleteResponse>>()
    val snackResponse: LiveData<Response<SnackDeleteResponse>> = _snackResponse

    fun addBeer(beerRequest: BeerRequest) = viewModelScope.launch {
        _beerResponse.value = repository.addBeer(beerRequest)
     //   getBeers()
    }
    fun addSnack(snackRequest: SnackRequest) = viewModelScope.launch {
        _snackResponse.postValue(repository.addSnack(snackRequest))
    }
}