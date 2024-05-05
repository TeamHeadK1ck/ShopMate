package com.example.purchaselist_project.data

import com.example.purchaselist_project.data.local.room.DbDao
import com.example.purchaselist_project.data.local.room.entity.CheckDb
import com.example.purchaselist_project.data.local.room.entity.UserDb
import com.example.purchaselist_project.domain.DomainRepository
import com.example.purchaselist_project.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.math.cos

class DataRepository(
    private val dao: DbDao
): DomainRepository() {
    override suspend fun getUsers(): List<UserDb> = dao.getAllUser()

    override suspend fun getUser(email: String): List<UserDb> = withContext(Dispatchers.IO) {
        val userList = dao.getUser(email)
        if (userList.isEmpty()) {
            return@withContext listOf()
        } else {
            return@withContext userList
        }
    }

    override suspend fun createUser(email: String, pass: String): Long {
        val user = UserDb(
            null,
            email,
            pass
        )
        return dao.createUser(user)
    }

    override suspend fun getCheckUser(idUser: Long): List<CheckDb> = dao.getCheckUser(idUser)

    override suspend fun createCheck(idUser: Long, name: String, cost: Long): Long {
        val newCheck = CheckDb(
            null,
            idUser,
            name,
            cost
        )
        return dao.createCheck(newCheck)
    }

    override suspend fun deleteCheck(userId: Long, nameCheck: String) =
        dao.deleteCheck(userId, nameCheck)
}