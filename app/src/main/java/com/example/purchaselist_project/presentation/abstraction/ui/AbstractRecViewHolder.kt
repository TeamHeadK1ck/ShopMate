package com.example.purchaselist_project.presentation.abstraction.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class AbstractRecViewHolder<DATA: DataRecyclerView>(
    binding: ViewBinding
): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(data: DATA)
}

interface DataRecyclerView