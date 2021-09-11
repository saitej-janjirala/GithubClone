package com.saitejajanjirala.githubclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saitejajanjirala.githubclone.R
import com.saitejajanjirala.githubclone.databinding.SearchItemBinding
import com.saitejajanjirala.githubclone.models.Item
import com.saitejajanjirala.githubclone.utils.OnItemClickListener

class MainAdapter(
    private val context: Context,
    private val onItemClickListener: OnItemClickListener):ListAdapter<Item,MainAdapter.MainViewHolder>(DataItemCallback()){
    inner class MainViewHolder(val binding:  SearchItemBinding) :RecyclerView.ViewHolder(binding.root){
            init {
                binding.root.setOnClickListener {
                    val position=bindingAdapterPosition
                    if(position!=RecyclerView.NO_POSITION) {
                        onItemClickListener.onClick(position)
                    }
                }
            }
            fun bind(position: Int){
                val item=getItem(position)
                binding.apply {
                    Glide.with(context)
                        .load(item.owner!!.avatar_url)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(avatar)
                    repoName.text="RepoName:- ${item.name}"
                    userName.text="UserName:- ${item.owner?.login}"
                    repoStars.text="Stars:- ${item?.stargazers_count}"
                    repoForks.text="Forks:- ${item.forks}"
                    repoIssues.text="Open issues:- ${item.open_issues_count}"
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.search_item,parent,false)
        val binding=SearchItemBinding.bind(view)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        if(position!=RecyclerView.NO_POSITION){
            holder.bind(position)
        }
    }

    class DataItemCallback:DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item)=oldItem.id==newItem.id

        override fun areContentsTheSame(oldItem: Item, newItem: Item)= oldItem == newItem

    }


}