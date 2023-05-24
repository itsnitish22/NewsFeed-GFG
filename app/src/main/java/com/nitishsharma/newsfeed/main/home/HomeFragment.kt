package com.nitishsharma.newsfeed.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitishsharma.newsfeed.api.models.Item
import com.nitishsharma.newsfeed.databinding.FragmentHomeBinding
import com.nitishsharma.newsfeed.main.utils.Utility.toast

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeVM: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentHomeBinding.inflate(layoutInflater, container, false).also {
        binding = it
        showTopAppBar() //show the top app bar
        getNews() //get news
    }.root

    private fun getNews() {
        homeVM.getNews() //getting news from api
    }

    //setup top app bar
    private fun showTopAppBar() {
        val topAppBar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(topAppBar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers() //init observers
        initClickListeners() //init  click listeners
    }

    private fun initClickListeners() {
        binding.swipeRefresh.setOnRefreshListener { //swipe refresh
            getNews() // get news from api
        }
    }

    private fun initObservers() {
        homeVM.fetchedNews.observe(requireActivity(), Observer {//observer for news
            setUpRecyclerView(it.items) //setup recycler view
        })
        homeVM.isLoading.observe(requireActivity(), Observer {//observer for loading
            binding.progressBar.visibility =
                if (it) View.VISIBLE else View.GONE //if loading is true, make progress bar visible, else hide it
        })
        homeVM.errorMsg.observe(requireActivity(), Observer { errorMsg ->
            errorMsg?.let {
                binding.progressBar.visibility = View.GONE
                toast(it)
            }
        })
    }

    private fun setUpRecyclerView(items: ArrayList<Item>) {
        //setting up recycler view
        with(binding) {
            recyclerView.adapter = NewsAdapter(layoutInflater, items)
            recyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            if (swipeRefresh.isRefreshing)
                swipeRefresh.isRefreshing = false
        }
    }
}