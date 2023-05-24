package com.nitishsharma.newsfeed.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitishsharma.newsfeed.api.RetrofitInstance
import com.nitishsharma.newsfeed.api.models.News
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel : ViewModel() {
    private val _fetchedNews: MutableLiveData<News> = MutableLiveData()
    val fetchedNews: LiveData<News>
        get() = _fetchedNews

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getNews() {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true) //post loading status
                val response = RetrofitInstance.api.getNewNews() //fetch news from api
                _fetchedNews.postValue(response.body()) //post news
                _isLoading.postValue(false) //post loading status
            } catch (e: IOException) {
                Log.e("HOME_FRAG_VM", e.toString())
            }
        }
    }
}