package taki.eddine.premier.league.pro.livescoresdata


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "liveScoresTable",indices = [Index(value = ["matchID"],unique = true)])
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

    override fun describeContents(): Int {
        return describeContents()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {

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