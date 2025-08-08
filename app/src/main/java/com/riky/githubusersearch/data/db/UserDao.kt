package com.riky.githubusersearch.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM github_user WHERE username LIKE :query")
    suspend fun searchUser(query: String): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM github_user WHERE username = :username")
    suspend fun getUserDetail(username: String): UserEntity?
}
