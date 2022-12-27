package com.submission.picodiploma.aplikasigithubusers_navigationapi.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: MutableList<ItemsItem>
)
@Parcelize
@Entity(tableName = "user")
data class ItemsItem(

	@ColumnInfo(name = "login")
	val login: String? = null,

	@ColumnInfo(name = "url")
	val url: String? = null,

	@ColumnInfo(name = "avatarUrl")
	val avatar_url: String? = null,

	@PrimaryKey
	@ColumnInfo(name = "id")
	val id: Int


): Parcelable
