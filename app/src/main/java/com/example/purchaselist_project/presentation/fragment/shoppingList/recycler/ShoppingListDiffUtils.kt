package com.example.purchaselist_project.presentation.fragment.shoppingList.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.purchaselist_project.presentation.dataUi.CheckUi

class ShoppingListDiffUtils(
    private val oldList: List<CheckUi>,
    private val newList: List<CheckUi>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = when {
        oldList[oldItemPosition].name == newList[newItemPosition].name -> true
        oldList[oldItemPosition].cost == newList[newItemPosition].cost -> true
        else -> false
    }
}