package com.example.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.newsapiclient.databinding.FragmentSavedBinding
import com.example.newsapiclient.presentation.adapter.NewsAdapter
import com.example.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedFragment : Fragment() {
        private lateinit var binding: FragmentSavedBinding
        private lateinit var viewModel: NewsViewModel
        private lateinit var newsadapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsadapter = (activity as MainActivity).newsAdapter


        newsadapter.setOnItemClickListener {
            if(it.equals(null)){
                Toast.makeText(activity,"No article avaible", Toast.LENGTH_SHORT).show()
            }else{
                val bundle = Bundle().apply {
                    putSerializable("selected_article",it)

                }
                findNavController().navigate(R.id.action_savedFragment_to_infoFragment,bundle)

            }

        }

        inItRecyclerView()

        viewModel.getSavedNews().observe(viewLifecycleOwner,{
            newsadapter.differ.submitList(it)
        })


        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
              return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val  position = viewHolder.adapterPosition
                val  article = newsadapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view,"Successfully deleted",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo",{
                        viewModel.saveArticle(article)
                    })
                    show()
                }

            }

        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSaved)
        }


    }

    private fun inItRecyclerView(){
        binding.rvSaved.apply {
            adapter= newsadapter
            layoutManager = LinearLayoutManager(activity)
        }
    }





}