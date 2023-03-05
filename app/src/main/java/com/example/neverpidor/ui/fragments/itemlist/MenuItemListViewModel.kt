package com.example.neverpidor.ui.fragments.itemlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neverpidor.Event
import com.example.neverpidor.data.MenuItemsRepository
import com.example.neverpidor.model.beer.BeerList
import com.example.neverpidor.model.beer.BeerPostResponse
import com.example.neverpidor.model.snack.SnackDeleteResponse
import com.example.neverpidor.model.snack.SnackList
import kotlinx.coroutines.launch
import retrofit2.Response

class MenuItemListViewModel: ViewModel() {

    private val repository = MenuItemsRepository()

    private val _snacks = MutableLiveData<SnackList>()
    val snacks: LiveData<SnackList> = _snacks

    private val _beers = MutableLiveData<BeerList>()
    val beers: LiveData<BeerList> = _beers

    private val _beerResponse = MutableLiveData<Event<Response<BeerPostResponse>>>()
    val beerResponse: LiveData<Event<Response<BeerPostResponse>>> = _beerResponse

    private val _snackResponse = MutableLiveData<Event<Response<SnackDeleteResponse>>>()
    val snackResponse: LiveData<Event<Response<SnackDeleteResponse>>> = _snackResponse

    fun getSnacks() = viewModelScope.launch {
        _snacks.postValue(repository.getSnacks())
    }
    fun getBeers() = viewModelScope.launch {
         repository.getBeers()?.collect{
             _beers.value = it
        }
    }
    fun deleteBeer(beerId: String) = viewModelScope.launch {
        _beerResponse.postValue(Event(repository.deleteBeer(beerId)))
        getBeers()
    }
    fun deleteSnack(snackId: String) = viewModelScope.launch {
        _snackResponse.postValue(Event(repository.deleteSnack(snackId)))
        getSnacks()
    }
}