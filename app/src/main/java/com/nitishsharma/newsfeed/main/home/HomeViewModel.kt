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

const val TAG_HOME_VM = "HOME_FRAG_VM"

class HomeViewModel : ViewModel() {
    private val _fetchedNews: MutableLiveData<News> = MutableLiveData()
    val fetchedNews: LiveData<News>
        get() = _fetchedNews

    fun getNews() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getNews()
                _fetchedNews.postValue(response.body())
            } catch (e: IOException) {
                Log.e(TAG_HOME_VM, e.toString())
            }
        }
    }
}