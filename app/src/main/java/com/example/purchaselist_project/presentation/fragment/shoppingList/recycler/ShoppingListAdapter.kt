package com.example.purchaselist_project.presentation.fragment.shoppingList.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.purchaselist_project.R
import com.example.purchaselist_project.databinding.ShoppingRecItemBinding
import com.example.purchaselist_project.presentation.dataUi.CheckUi

class ShoppingListAdapter(
    private val onClick: View.OnClickListener
): RecyclerView.Adapter<ShoppingListViewHolder>(), View.OnClickListener {
    private val currentList = mutableListOf<CheckUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder =
        ShoppingListViewHolder(
            ShoppingRecItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            this
        )

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun startDiffUtils(newList: List<CheckUi>) {
        val diffUtil = ShoppingListDiffUtils(currentList, newList)
        val diffCalculate = DiffUtil.calculateDiff(diffUtil)
        currentList.clear()
        currentList.addAll(newList)
        diffCalculate.dispatchUpdatesTo(this)
    }

    fun updateData(newList: List<CheckUi>) = startDiffUtils(newList)

    fun addItem(newItem: CheckUi?) {
        if(newItem == null) return
        val newList = mutableListOf<CheckUi>()
        newList.addAll(currentList)
        newList.add(newItem)
        startDiffUtils(newList)
    }

    fun getSum(): Long {
        var result: Long = 0
        currentList.forEach {
            result+=it.cost
        }
        return result
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.delete_shopping_item -> {
                val newList = ArrayList(currentList)
                newList.remove(v.tag as CheckUi)
                startDiffUtils(newList)
                onClick.onClick(v)
            }
        }
    }
}