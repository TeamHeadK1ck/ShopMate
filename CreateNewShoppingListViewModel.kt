package com.example.purchaselist_project.presentation.fragment.shoppingList.utils

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

class CreateNewShoppingListViewModel(private val repository: DomainRepository): ViewModel() {
    private lateinit var user: UserDb
    private var generateId: Long = 0

    private val _addCheckItem = MutableStateFlow<CheckUi?>(null)
    val addCheckItem: StateFlow<CheckUi?>
        get() = _addCheckItem

    fun getShoppingList(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            user = repository.getUser(email)[0]
            val shoppingList = repository.getCheckUser(user.id!!)
            generateId = shoppingList.size.toLong()
        }
    }

    fun addCheck(name:String, cost: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            generateId++
            val newCheck = CheckDb(
                null,
                user.id!!,
                name,
                cost
            )
            val dbId = repository.createCheck(newCheck.userId, newCheck.name, newCheck.cost)
            _addCheckItem.value = CheckUi(dbId, name, cost)
            delay(1000)
        }
    }
}