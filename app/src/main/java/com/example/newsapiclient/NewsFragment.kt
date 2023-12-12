package com.example.newsapiclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.databinding.FragmentNewsBinding
import com.example.newsapiclient.presentation.adapter.NewsAdapter
import com.example.newsapiclient.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter:NewsAdapter
    private var isScrolling = false
    private var isloading = false
    private var isLastPage = false
    private var pages = 0

    private var country = "us"
    private var page = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)


         viewModel = (activity as MainActivity).viewModel
        adapter = (activity as MainActivity).newsAdapter



        adapter.setOnItemClickListener {
            if(it.equals(null)){
            Toast.makeText(activity,"No article avaible",Toast.LENGTH_SHORT).show()
            }else{
                val bundle = Bundle().apply {
                    putSerializable("selected_article",it)

                }
                findNavController().navigate(R.id.action_newsFragment_to_infoFragment,bundle)

            }

        }
        inItRecyclerView()
        viewNewsList()
        setSearchView()


    }

    private fun viewNewsList() {
        viewModel.getNewsHeadlines(country,page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let {
                        Toast.makeText(activity, "An error occurred " + it, Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        adapter.differ.submitList(it.articles.toList())
                        if (it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        }
                        else{
                            pages = it.totalResults/20 +1
                        }
                        isLastPage = page ==pages

                    }

                }


            }

        }
    }

    private fun inItRecyclerView() {

        binding.rvNews.adapter = adapter
        binding.rvNews.layoutManager = LinearLayoutManager(activity)
        binding.rvNews.addOnScrollListener(this@NewsFragment.onScrollListener)

    }

    private fun hideProgressBar(){
        isloading = false
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        isloading = true
        binding.progressBar.visibility = View.VISIBLE
    }

    private var onScrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItemCount = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition+visibleItemCount >= sizeOfTheCurrentList

            val shouldPaginate = !isLastPage && !isloading && hasReachedToEnd && isScrolling
            if (shouldPaginate){
                page++
                viewModel.getNewsHeadlines(country,page)
                isScrolling= false
            }

        }

    }



    //search




    fun viewSearchedNews(){

        if (view != null){
            viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Loading -> {
                        showProgressBar()
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message.let {
                            Toast.makeText(activity, "An error occurred " + it, Toast.LENGTH_SHORT)
                                .show()
                        }

                    }

                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            adapter.differ.submitList(it.articles.toList())
                            if (it.totalResults % 20 == 0) {
                                pages = it.totalResults / 20
                            }
                            else{
                                pages = it.totalResults/20 +1
                            }
                            isLastPage = page ==pages

                        }

                    }


                }

            }
        }

        

    }


    fun setSearchView(){
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNews("us",1,query.toString())
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.searchNews("us",1,newText.toString())
                    viewSearchedNews()
                }

                return false
            }

        })

        binding.searchView.setOnCloseListener(object :SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                inItRecyclerView()
                viewNewsList()
                return false
            }

        })


    }



}