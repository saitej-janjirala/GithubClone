package com.saitejajanjirala.githubclone.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.saitejajanjirala.githubclone.R
import com.saitejajanjirala.githubclone.adapter.MainAdapter
import com.saitejajanjirala.githubclone.base.BaseActivity
import com.saitejajanjirala.githubclone.databinding.ActivityMainBinding
import com.saitejajanjirala.githubclone.di.component.ActivityComponent
import com.saitejajanjirala.githubclone.models.Item
import com.saitejajanjirala.githubclone.utils.RxSearchObservable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saitejajanjirala.githubclone.ui.detail.DetailActivity
import com.saitejajanjirala.githubclone.utils.Keys
import com.saitejajanjirala.githubclone.utils.OnItemClickListener


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private lateinit var mainAdapter: MainAdapter
    private lateinit var listItems: List<Item>
    private lateinit var layoutManager: LinearLayoutManager
    private  var isLoading:Boolean=false
    private var onItemClickListener=object:OnItemClickListener{
        override fun onClick(position: Int) {
            val intent= Intent(this@MainActivity,DetailActivity::class.java)
            intent.putExtra(Keys.ITEM,listItems[position])
            startActivity(intent)
        }

    }
    override fun setObservers() {
        super.setObservers()
      viewModel.setSubject(RxSearchObservable.fromView(binding.searchView))

        viewModel.listItems.observe(this, Observer {
            listItems = it
            mainAdapter.submitList(listItems)
        })

        viewModel.searchNetworkCall.observe(this, {
            it?.let { x ->
                if (x) {
                    binding.centerProgressBar.visibility = View.VISIBLE
                } else {
                    binding.centerProgressBar.visibility = View.GONE
                }
            }
        })
        viewModel.paginatedNetworkCall.observe(this, {
            it?.let { x ->
                isLoading=x
                if (x) {
                    binding.belowProgressBar.visibility = View.VISIBLE
                } else {
                    binding.belowProgressBar.visibility = View.GONE
                }
            }
        })


        binding.searchRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { //check for scroll down
                    val visibleItemCount: Int = layoutManager.childCount
                    val totalItemCount: Int = layoutManager.itemCount
                    val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                    if (!isLoading) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                            viewModel.getData()
                        }
                    }
                }
            }
        })

    }

    override fun initializeBinding(view: View) {
        binding = ActivityMainBinding.bind(view)
    }

    override fun provideLayoutId() =
        R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setUpView(savedInstanceState: Bundle?) {
        layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        mainAdapter = MainAdapter(this,onItemClickListener)
        listItems = mutableListOf<Item>()
        mainAdapter.submitList(listItems)
        binding.searchRecyclerview.layoutManager=layoutManager
        binding.searchRecyclerview.adapter = mainAdapter
    }

}