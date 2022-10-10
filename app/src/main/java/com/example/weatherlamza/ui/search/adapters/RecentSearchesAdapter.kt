package com.example.weatherlamza.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherlamza.databinding.ItemRecentlySearchedBinding

class RecentSearchesAdapter :
    RecyclerView.Adapter<RecentSearchesAdapter.RecentSearchesViewHolder>() {

    private var _binding: ItemRecentlySearchedBinding? = null
    private val binding: ItemRecentlySearchedBinding get() = _binding!!

    var recentlySearchedQueries = emptyList<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchesViewHolder {
        _binding =
            ItemRecentlySearchedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSearchesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentSearchesViewHolder, position: Int) {
        holder.query = recentlySearchedQueries[position]
    }

    override fun getItemCount(): Int = recentlySearchedQueries.size

    inner class RecentSearchesViewHolder(val binding: ItemRecentlySearchedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var query: String? = null
            set(value) {
                field = value
                if (value != null) {
                    binding.searchedItem.text = value
                }
            }
    }
}
