package com.example.purchaselist_project.presentation.fragment.shoppingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purchaselist_project.data.local.room.entity.CheckDb
import com.example.purchaselist_project.data.local.room.entity.UserDb
import com.example.purchaselist_project.domain.DomainRepository
import com.example.purchaselist_project.presentation.dataUi.CheckUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShoppingListFragmentViewModel(private val repository: DomainRepository): ViewModel() {
    private lateinit var user: UserDb
    private var generateId: Long = 0

    private val _shoppingItem = MutableStateFlow<List<CheckUi>>(listOf())
    val shoppingItem: StateFlow<List<CheckUi>>
        get() = _shoppingItem

    private val _addCheckItem = MutableStateFlow<CheckUi?>(null)
    val addCheckItem: StateFlow<CheckUi?>
        get() = _addCheckItem

    fun getShoppingList(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            user = repository.getUser(email)[0]
            val shoppingList = repository.getCheckUser(user.id!!)
            generateId = shoppingList.size.toLong()
            _shoppingItem.value = shoppingList.map { it.toCheck() }
        }
    }

    fun addCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            generateId++
            val newCheck = CheckDb(
                null,
                user.id!!,
                "список №$generateId",
                5000
            )
            repository.createCheck(newCheck.userId, newCheck.name, newCheck.cost)
            _addCheckItem.value = CheckUi("список №$generateId", 5000)
        }
    }

    fun deleteCheck(nameCheck: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCheck(user.id!!, nameCheck)
        }
    }
}