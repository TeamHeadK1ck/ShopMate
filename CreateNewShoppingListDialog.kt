package com.example.purchaselist_project.presentation.fragment.shoppingList.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.purchaselist_project.App
import com.example.purchaselist_project.R
import com.example.purchaselist_project.databinding.FragmentCreateNewShoppingListDialogBinding
import com.example.purchaselist_project.presentation.abstraction.ui.AbstractFragment
import com.example.purchaselist_project.presentation.fragment.shoppingList.ShoppingListFragmentArgs
import com.example.purchaselist_project.presentation.fragment.shoppingList.ShoppingListFragmentDirections
import com.example.purchaselist_project.presentation.fragment.shoppingList.ShoppingListFragmentViewModel
import com.example.purchaselist_project.presentation.fragment.shoppingList.recycler.ShoppingListAdapter
import kotlinx.coroutines.launch
import java.lang.Error

class CreateNewShoppingListDialog : AbstractFragment<FragmentCreateNewShoppingListDialogBinding>(R.layout.fragment_create_new_shopping_list_dialog), View.OnClickListener {
    private val args: CreateNewShoppingListDialogArgs by navArgs()
    private lateinit var email: String
    private lateinit var viewModel: CreateNewShoppingListViewModel

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateNewShoppingListDialogBinding =
        FragmentCreateNewShoppingListDialogBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = args.email
        viewModel = (requireActivity().application as App).viewModelFactory().create(CreateNewShoppingListViewModel::class.java)
        with(binding) {
            saveNewShoppingList.setOnClickListener(this@CreateNewShoppingListDialog)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.addCheckItem.collect{ newItem->
                    if(newItem != null) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
        viewModel.getShoppingList(email)
    }

    private fun validate(): Boolean {
        try {
            val transformName = binding.nameNewShoppingList.text.toString()
            val transformCost = binding.costNewShoppingList.text.toString().toLong()
            if(transformCost < 0 && (transformName.isEmpty() || processString(transformName) == "")) return false
            return true
        }
        catch(_: Error){
            return false
        }
    }

    private fun processString(input: String): String {
        var startIndex = 0
        var endIndex = input.length

        for (i in input.indices) {
            if (!input[i].isWhitespace()) {
                startIndex = i
                break
            }
        }

        for (i in input.length - 1 downTo 0) {
            if (!input[i].isWhitespace()) {
                endIndex = i + 1
                break
            }
        }

        return input.substring(startIndex, endIndex)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.saveNewShoppingList -> with(binding) {
                if(validate()) viewModel.addCheck(nameNewShoppingList.text.toString(), costNewShoppingList.text.toString().toLong())
                else {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка ввода данных!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}