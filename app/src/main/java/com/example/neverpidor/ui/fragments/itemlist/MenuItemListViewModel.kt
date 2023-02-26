package com.example.neverpidor.ui.fragments.itemlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neverpidor.data.MenuItemsRepository
import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerPostResponse
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackList
import com.example.neverpidor.network.SimpleResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MenuItemListViewModel: ViewModel() {

    private val repository = MenuItemsRepository()

    private val _snacks = MutableLiveData<SnackList>()
    val snacks: LiveData<SnackList> = _snacks

    private val _beers = MutableLiveData<BeerList>()
    val beers: LiveData<BeerList> = _beers

    private val _beerResponse = MutableLiveData<Response<BeerPostResponse>>()
    val beerResponse: LiveData<Response<BeerPostResponse>> = _beerResponse

    fun getSnacks() = viewModelScope.launch {
        _snacks.postValue(repository.getSnacks())
    }
    fun getBeers() = viewModelScope.launch {
        _beers.value = repository.getBeers()
    }

    fun addBeer(beerRequest: BeerRequest) = viewModelScope.launch {
        _beerResponse.value = repository.addBeer(beerRequest)
        getBeers()
    }
}