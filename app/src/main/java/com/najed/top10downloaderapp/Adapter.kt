package com.najed.top10downloaderapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.najed.top10downloaderapp.databinding.AppItemBinding
import com.najed.top10downloaderapp.model.Entry

class Adapter(val appsList: ArrayList<Entry>): RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    class ItemViewHolder (val binding: AppItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(AppItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            appTitleTv.text = appsList[position].title
        }
    }

    override fun getItemCount() = appsList.size
}