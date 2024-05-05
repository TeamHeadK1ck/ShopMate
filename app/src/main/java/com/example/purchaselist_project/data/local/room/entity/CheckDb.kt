package com.example.purchaselist_project.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.purchaselist_project.presentation.dataUi.CheckUi

@Entity(
    tableName = "CheckDb"
)
class CheckDb(
    @PrimaryKey
    val id: Long?,
    val userId: Long,
    val name: String,
    val cost: Long,
) {
    fun toCheck(): CheckUi = CheckUi(name, cost)

    companion object {
        fun createCheckDb(userId: Long, check: CheckUi): CheckDb =
            CheckDb(null, userId, check.name, check.cost)
    }
}