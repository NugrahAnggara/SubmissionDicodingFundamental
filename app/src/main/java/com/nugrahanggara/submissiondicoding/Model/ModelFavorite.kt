package com.nugrahanggara.submissiondicoding.Model

import android.os.Parcel
import android.os.Parcelable

data class ModelFavorite(
    val id: String?,
    val login: String?,
    val avatar: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(login)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelFavorite> {
        override fun createFromParcel(parcel: Parcel): ModelFavorite {
            return ModelFavorite(parcel)
        }

        override fun newArray(size: Int): Array<ModelFavorite?> {
            return arrayOfNulls(size)
        }
    }
}
