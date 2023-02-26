package com.example.neverpidor.ui.fragments.itemlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neverpidor.data.MenuItemsRepository
import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerRequest
import com.example.neverpidor.model.snack.SnackList
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.await

class MenuItemListViewModel: ViewModel() {

    private val repository = MenuItemsRepository()

    private val _snacks = MutableLiveData<SnackList>()
    val snacks: LiveData<SnackList> = _snacks

    private val _beers = MutableLiveData<BeerList>()
    val beers: LiveData<BeerList> = _beers

    private val _beerResponse = MutableLiveData<Call<BeerRequest>>()
    val beerResponse: LiveData<Call<BeerRequest>> = _beerResponse

    fun getSnacks() = viewModelScope.launch {
        _snacks.value = repository.getSnacks()
    }
    fun getBeers() = viewModelScope.launch {
        _beers.value = repository.getBeers()
    }

    fun addBeer(beerRequest: BeerRequest) = viewModelScope.async {
        _beerResponse.value = repository.addBeer(beerRequest)
        delay(200)
        getBeers()
    }

}