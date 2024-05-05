package com.example.purchaselist_project.domain

import com.example.purchaselist_project.data.local.room.entity.CheckDb
import com.example.purchaselist_project.data.local.room.entity.UserDb
import kotlinx.coroutines.Job

abstract class DomainRepository {
    //users
    abstract suspend fun getUsers() : List<UserDb>
    abstract suspend fun getUser(email: String) : List<UserDb>
    abstract suspend fun createUser(email: String, pass: String): Long
    //check
    abstract suspend fun getCheckUser(idUser: Long) : List<CheckDb>
    abstract suspend fun createCheck(idUser: Long, name: String, cost: Long): Long
    abstract suspend fun deleteCheck(userId: Long, nameCheck: String)
}