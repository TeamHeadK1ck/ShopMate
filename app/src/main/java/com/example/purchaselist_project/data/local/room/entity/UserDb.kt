package com.example.purchaselist_project.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "UserDb"
)
data class UserDb(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val email: String,
    val pass: String, //TODO сделать кэширование пароля (хранишь хэш пароля, сравниваешь с введеным хэшем, если равны то true)
) {
    fun toUser() {}

    companion object {
        fun createUserDb(){}
    }
}