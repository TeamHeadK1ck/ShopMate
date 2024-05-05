package com.example.purchaselist_project.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "CheckDbItem"
)
class CheckDbItem(
    @PrimaryKey
    val id: Long?,
    val checkId: Long,
    val name: String,
    val singleCost: Long,
    val count: Long,
) {
    fun toCheckItem() {}

    companion object {
        fun createCheckDbItem(){}
    }
}