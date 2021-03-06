package com.example.lista

import androidx.room.*

@Dao
interface AfazerDao {
    @Query("select * from Afazer where id = :id")
    fun get(id: Long): Afazer

    @Query("select * from Afazer order by id DESC")
    fun getAll(): List<Afazer>

    @Insert
    fun insert(afazer: Afazer): Long

    @Delete
    fun delete(afazer: Afazer)

    @Update
    fun update(afazer: Afazer)
}
