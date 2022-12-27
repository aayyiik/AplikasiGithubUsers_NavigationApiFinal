package com.submission.picodiploma.aplikasigithubusers_navigationapi.room


import androidx.lifecycle.LiveData
import androidx.room.*
import com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model.ItemsItem

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ItemsItem)

    @Query("SELECT * FROM user WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): ItemsItem

    @Delete
    fun delete(user: ItemsItem)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun getAllUser(): LiveData<MutableList<ItemsItem>>
}