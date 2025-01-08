package com.example.communicate.data.local

import androidx.room.*

@Dao
interface RandomStringDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(randomString: RandomStringEntity)

    @Query("SELECT * FROM random_string_table")
    suspend fun getAllRandomStrings(): List<RandomStringEntity>

    @Query("DELETE FROM random_string_table WHERE id = :id")
    suspend fun deleteSpecificStringById(id: Int)

    @Query("DELETE FROM random_string_table")
    suspend fun deleteAllStrings()
}
