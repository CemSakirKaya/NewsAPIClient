package com.example.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.newsapiclient.databinding.FragmentInfoBinding
import com.example.newsapiclient.databinding.FragmentNewsBinding
import com.example.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel:NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        val  args : InfoFragmentArgs by navArgs()

        val article = args.selectedArticle

        viewModel = (activity as MainActivity).viewModel

        binding.webview.apply {

                webViewClient = WebViewClient()
                if(article.url?.isNotEmpty() == true){
                    loadUrl(article.url.toString())

            }

          //  if (article.url != ""){
            //    article.url?.let { loadUrl(it)






        }

        binding.fab.setOnClickListener{
           viewModel.saveArticle(article)
            Snackbar.make(it,"Saved successfully",Snackbar.LENGTH_SHORT).show()
        }

    }




}