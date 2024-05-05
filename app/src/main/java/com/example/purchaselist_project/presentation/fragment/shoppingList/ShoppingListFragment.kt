package com.example.purchaselist_project.presentation.fragment.shoppingList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.purchaselist_project.App
import com.example.purchaselist_project.R
import com.example.purchaselist_project.databinding.FragmentShoppingListBinding
import com.example.purchaselist_project.presentation.abstraction.ui.AbstractFragment
import com.example.purchaselist_project.presentation.dataUi.CheckUi
import com.example.purchaselist_project.presentation.fragment.shoppingList.recycler.ShoppingListAdapter
import kotlinx.coroutines.launch

class ShoppingListFragment : AbstractFragment<FragmentShoppingListBinding>(R.layout.fragment_shopping_list), View.OnClickListener {
    private val args: ShoppingListFragmentArgs by navArgs()
    private lateinit var adapter: ShoppingListAdapter
    private lateinit var viewModel: ShoppingListFragmentViewModel
    private lateinit var email: String

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShoppingListBinding = FragmentShoppingListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        email = args.email
        viewModel = (requireActivity().application as App).viewModelFactory().create(ShoppingListFragmentViewModel::class.java)
        adapter = ShoppingListAdapter(this)
        with(binding) {
            shoppingRecView.adapter = adapter
            addCheckButton.setOnClickListener(this@ShoppingListFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.shoppingItem.collect { checkList->
                Log.d("testCheckList", "$email: $checkList")
                if(checkList.isEmpty()) {
                    binding.emptyShoppingListLayout.visibility = View.VISIBLE
                    binding.shoppingRecView.visibility = View.INVISIBLE
                }
                else {
                    binding.emptyShoppingListLayout.visibility = View.INVISIBLE
                    binding.shoppingRecView.visibility = View.VISIBLE
                    adapter.updateData(checkList)
                    binding.sumCostAllCheckText.text = "${adapter.getSum()} руб."
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addCheckItem.collect{ newItem->
                if(newItem != null) {
                    binding.emptyShoppingListLayout.visibility = View.INVISIBLE
                    binding.shoppingRecView.visibility = View.VISIBLE
                    adapter.addItem(newItem)
                    binding.sumCostAllCheckText.text = "${adapter.getSum()} руб."
                }
            }
        }

        viewModel.getShoppingList(email)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.addCheckButton -> {
                Log.d("testCheckList", "клик на новый итем!")
                viewModel.addCheck()
            }

            R.id.delete_shopping_item -> {
                viewModel.deleteCheck((v.tag as CheckUi).name)
                binding.sumCostAllCheckText.text = "${adapter.getSum()} руб."
                if(adapter.itemCount == 0) {
                    binding.emptyShoppingListLayout.visibility = View.VISIBLE
                    binding.shoppingRecView.visibility = View.INVISIBLE
                }
            }
        }
    }
}