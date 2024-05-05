package com.example.purchaselist_project.presentation.fragment.shoppingList.recycler

import android.view.View
import com.example.purchaselist_project.databinding.ShoppingRecItemBinding
import com.example.purchaselist_project.presentation.abstraction.ui.AbstractRecViewHolder
import com.example.purchaselist_project.presentation.dataUi.CheckUi

class ShoppingListViewHolder(
    private val binding: ShoppingRecItemBinding,
    private val onClick: View.OnClickListener
): AbstractRecViewHolder<CheckUi>(binding) {
    override fun bind(data: CheckUi) {
        with(binding) {
            shoppingListItemName.text = data.name
            deleteShoppingItem.setOnClickListener {
                it.tag = data
                onClick.onClick(it)
            }
        }
    }
}