package taki.eddine.premier.league.pro.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class AwayLogoModel (
    @SerializedName("strTeamBadge")
    val AwaystrTeamBadge: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(AwaystrTeamBadge)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AwayLogoModel> {
        override fun createFromParcel(parcel: Parcel): AwayLogoModel {
            return AwayLogoModel(parcel)
        }

        override fun newArray(size: Int): Array<AwayLogoModel?> {
            return arrayOfNulls(size)
        }
    }
}