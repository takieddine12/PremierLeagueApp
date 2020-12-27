package taki.eddine.premier.league.pro.topscorersui


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ResultX(

    @SerializedName("team_logo")
    val teamLogo: String?

)  : Parcelable{
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun describeContents(): Int {
        return describeContents()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {

    }

    companion object CREATOR : Parcelable.Creator<ResultX> {
        override fun createFromParcel(parcel: Parcel): ResultX {
            return ResultX(parcel)
        }

        override fun newArray(size: Int): Array<ResultX?> {
            return arrayOfNulls(size)
        }
    }

}