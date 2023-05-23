package com.nitishsharma.newsfeed.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.nitishsharma.newsfeed.databinding.FragmentHomeBinding

const val TAG_HOME = "HOME_FRAG"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeVM: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentHomeBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hitApiCalls()
        initObservers()
    }

    private fun hitApiCalls() {
        homeVM.getNews()
    }

    private fun initObservers() {
        homeVM.fetchedNews.observe(requireActivity(), Observer {
            Log.i(TAG_HOME, it.toString())
        })
    }

}