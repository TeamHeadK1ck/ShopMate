package com.example.purchaselist_project.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.purchaselist_project.data.local.room.entity.CheckDb
import com.example.purchaselist_project.data.local.room.entity.CheckDbItem
import com.example.purchaselist_project.data.local.room.entity.UserDb

@Database(
    entities = [
        UserDb::class,
        CheckDb::class,
        CheckDbItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PurchaseListProjectDataBase: RoomDatabase() {
    abstract fun getDao(): DbDao

    companion object {
        fun createDb(context: Context): PurchaseListProjectDataBase = Room.databaseBuilder(
            context,
            PurchaseListProjectDataBase::class.java,
            " PurchaseListDb"
        ).fallbackToDestructiveMigration().build()
    }
}