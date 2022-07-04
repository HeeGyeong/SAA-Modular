package com.example.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Movie
import com.example.search.databinding.ItemMovieBinding

class SearchAdapter(private val itemClick: (Movie) -> Unit) :
    ListAdapter<Movie, SearchAdapter.SearchViewHolder>(
        diffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )

        return SearchViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                itemClick(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(holderSearch: SearchViewHolder, position: Int) {
        holderSearch.bind(getItem(position))
    }

    class SearchViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }

    /**
     * diff object
     *
     * 서로 같은 아이템인지 판단하기 위한 조건을 직접 추가한다.
     * title 을 사용하여 같은 아이템인지 여부를 판단하도록 하였다.
     */
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem.title == newItem.title
        }
    }
}
