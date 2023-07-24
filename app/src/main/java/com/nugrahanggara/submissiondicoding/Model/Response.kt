package com.nugrahanggara.submissiondicoding.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

): Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(login)
		parcel.writeString(avatarUrl)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ItemsItem> {
		override fun createFromParcel(parcel: Parcel): ItemsItem {
			return ItemsItem(parcel)
		}

		override fun newArray(size: Int): Array<ItemsItem?> {
			return arrayOfNulls(size)
		}
	}
}
