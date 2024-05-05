package com.example.purchaselist_project

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.purchaselist_project.data.DataRepository
import com.example.purchaselist_project.data.local.room.DbDao
import com.example.purchaselist_project.data.local.room.PurchaseListProjectDataBase
import com.example.purchaselist_project.domain.DomainRepository
import com.example.purchaselist_project.utils.factory.ViewModelFactory

class App: Application() {
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var repository: DomainRepository
    private lateinit var db: PurchaseListProjectDataBase

    override fun onCreate() {
        super.onCreate()
        initDb()
        repository = DataRepository(db.getDao())
        viewModelFactory = ViewModelFactory(repository)
    }
    private fun initDb() {
        db = PurchaseListProjectDataBase.createDb(this)
    }

    fun viewModelFactory(): ViewModelFactory = viewModelFactory
}