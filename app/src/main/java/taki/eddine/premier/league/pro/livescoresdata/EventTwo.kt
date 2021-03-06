package taki.eddine.premier.league.pro.livescoresdata


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "liveScoresTable")
data class EventTwo(
    @SerializedName("strHomeTeam")
    var strHomeTeam : String?,
    @SerializedName("strAwayTeam")
    var strAwayTeam : String?,
    @SerializedName("strHomeTeamBadge")
    var strHomeTeamBadge : String?,
    @SerializedName("strAwayTeamBadge")
    var strAwayTeamBadge : String?,
    @SerializedName("intHomeScore")
    var intHomeScore : Int?,
    @SerializedName("intAwayScore")
    var intAwayScore : Int?,
    @SerializedName("strProgress")
    var strProgress : String?,
    @SerializedName("strEventTime")
    var strEventTime : String?,
    @SerializedName("dateEvent")
    var dateEvent : String?,
    @SerializedName("updated")
    var updated : String?,
    @SerializedName("strLeague")
    var strLeague : String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var matchID : Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        matchID = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strHomeTeam)
        parcel.writeString(strAwayTeam)
        parcel.writeString(strHomeTeamBadge)
        parcel.writeString(strAwayTeamBadge)
        parcel.writeValue(intHomeScore)
        parcel.writeValue(intAwayScore)
        parcel.writeString(strProgress)
        parcel.writeString(strEventTime)
        parcel.writeString(dateEvent)
        parcel.writeString(updated)
        parcel.writeString(strLeague)
        parcel.writeValue(matchID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventTwo> {
        override fun createFromParcel(parcel: Parcel): EventTwo {
            return EventTwo(parcel)
        }

        override fun newArray(size: Int): Array<EventTwo?> {
            return arrayOfNulls(size)
        }
    }


}