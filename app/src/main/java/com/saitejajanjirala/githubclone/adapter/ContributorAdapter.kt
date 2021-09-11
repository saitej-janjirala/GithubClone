package com.saitejajanjirala.githubclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saitejajanjirala.githubclone.R
import com.saitejajanjirala.githubclone.databinding.ContributorItemBinding
import com.saitejajanjirala.githubclone.databinding.SearchItemBinding
import com.saitejajanjirala.githubclone.models.Contributor
import com.saitejajanjirala.githubclone.models.Item

class ContributorAdapter(private val context: Context) : ListAdapter<Contributor, ContributorAdapter.ContributorViewHolder>(DataItemCallback()){
    inner class ContributorViewHolder(val binding: ContributorItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val item=getItem(position)
            binding.apply {
                Glide.with(context)
                    .load(item.avatarUrl)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(avatar)
                name.text=item.login
                contributions.text="contributions:- ${item.contributions}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.contributor_item,parent,false)
        val binding= ContributorItemBinding.bind(view)
        return ContributorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        if(position!= RecyclerView.NO_POSITION){
            holder.bind(position)
        }
    }

    class DataItemCallback: DiffUtil.ItemCallback<Contributor>(){
        override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor)=oldItem.id==newItem.id

        override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor)= oldItem == newItem

    }
}