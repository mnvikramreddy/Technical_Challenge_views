package com.tps.challenge.features.storefeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tps.challenge.R
import com.tps.challenge.network.model.StoreResponse

/**
 * A RecyclerView.Adapter to populate the screen with a store feed.
 */
class StoreFeedAdapter(private val onStoreSelect: (String) -> Unit) :
    //RecyclerView.Adapter<StoreItemViewHolder>(){ // to enable recyclerview
    ListAdapter<StoreResponse, StoreItemViewHolder>(StoreDiffUtil()) { //to use list adapter

    /** Not Needed when using list adapter
     * */
    private var storeList: List<StoreResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreItemViewHolder {
        return StoreItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoreItemViewHolder, position: Int) {
        /**
         * use for recycler view
         * */
        //val store = storeList[position]

        /**
         * use for List Adapter
         * */
        val store = getItem(position)

        with(holder.itemView) {
            setOnClickListener { onStoreSelect(store.id) }
            findViewById<ImageView>(R.id.image).apply {
                Glide.with(this).load(store.coverImgUrl).into(this)
            }
            findViewById<TextView>(R.id.name).text = store.name
            findViewById<TextView>(R.id.description).text = store.description
        }
    }

/*
    *//**
     * Only need in Recycler view not for list Adapter
     * *//*
    override fun getItemCount(): Int {
        return storeList.size
    }

    *//**
     * Only need in Recycler view not for list Adapter
     * *//*
    fun updateList(list: List<StoreResponse>) {
        storeList = list
        notifyDataSetChanged()
    }
 */
}

/**
 * Holds the view for the Store item.
 */
class StoreItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


class StoreDiffUtil : DiffUtil.ItemCallback<StoreResponse>() {
    override fun areItemsTheSame(
        oldItem: StoreResponse,
        newItem: StoreResponse
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: StoreResponse,
        newItem: StoreResponse
    ): Boolean {
        return oldItem == newItem
    }
}