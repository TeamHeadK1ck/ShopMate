package com.example.purchaselist_project.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.purchaselist_project.data.local.room.entity.CheckDb
import com.example.purchaselist_project.data.local.room.entity.UserDb

@Dao
interface DbDao {
    ///user method
    @Insert
    fun createUser(user: UserDb): Long

    @Query("SELECT * FROM UserDb WHERE email = :email")
    fun getUser(email: String): List<UserDb>

    @Query("SELECT * FROM UserDb")
    fun getAllUser(): List<UserDb>

    ///check method
    @Insert
    fun createCheck(check: CheckDb): Long

    @Query("SELECT * FROM CheckDb WHERE userId = :idUser")
    fun getCheckUser(idUser: Long): List<CheckDb>

    @Query("DELETE FROM CheckDb WHERE userId = :userId AND name = :nameCheck")
    fun deleteCheck(userId: Long, nameCheck: String)
}