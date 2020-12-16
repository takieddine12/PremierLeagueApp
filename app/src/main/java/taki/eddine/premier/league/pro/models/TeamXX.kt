package taki.eddine.premier.league.pro.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class TeamXX(
    @SerializedName("strTeamBadge")
    val HomestrTeamBadge: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(HomestrTeamBadge)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TeamXX> {
        override fun createFromParcel(parcel: Parcel): TeamXX {
            return TeamXX(parcel)
        }

        override fun newArray(size: Int): Array<TeamXX?> {
            return arrayOfNulls(size)
        }
    }

}