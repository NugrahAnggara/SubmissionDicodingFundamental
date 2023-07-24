package com.nugrahanggara.submissiondicoding.Model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ModelUserResponse(

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(bio)
		parcel.writeString(login)
		parcel.writeValue(followers)
		parcel.writeString(avatarUrl)
		parcel.writeValue(following)
		parcel.writeString(name)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ModelUserResponse> {
		override fun createFromParcel(parcel: Parcel): ModelUserResponse {
			return ModelUserResponse(parcel)
		}

		override fun newArray(size: Int): Array<ModelUserResponse?> {
			return arrayOfNulls(size)
		}
	}
}