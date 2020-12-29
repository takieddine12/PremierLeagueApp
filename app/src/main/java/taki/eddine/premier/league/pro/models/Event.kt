package taki.eddine.premier.league.pro.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "eventTable")
data class Event(
    @Embedded val teamXX: TeamXX?,
    @Embedded  val awayLogo : AwayLogoModel?,
    @SerializedName("idAwayTeam")
    val idAwayTeam: String?,
    @SerializedName("idEvent")
    val idEvent: String?,
    @SerializedName("idHomeTeam")
    val idHomeTeam: String?,
    @SerializedName("intAwayScore")
    var intAwayScore: String? = "0",
    @SerializedName("intHomeScore")
    var intHomeScore: String?,
    @SerializedName("intRound")
    val intRound: String?,
    @SerializedName("strAwayGoalDetails")
    val strAwayGoalDetails: String?,
    @SerializedName("strAwayRedCards")
    val strAwayRedCards: String?,
    @SerializedName("strAwayTeam")
    val strAwayTeam: String?,
    @SerializedName("strAwayYellowCards")
    val strAwayYellowCards: String?,
    @SerializedName("strHomeGoalDetails")
    val strHomeGoalDetails: String?,
    @SerializedName("strHomeRedCards")
    val strHomeRedCards: String?,
    @SerializedName("strHomeTeam")
    val strHomeTeam: String?,
    @SerializedName("strHomeYellowCards")
    val strHomeYellowCards: String?,
    @SerializedName("dateEvent")
    val dateEvent : String?,
    @SerializedName("strTime")
    val strTime : String?,
    @SerializedName("strPostponed")
    var strPostponed : String?


) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var fixturesID: Int? = null
    var matchRound: Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(TeamXX::class.java.classLoader),
        parcel.readParcelable(AwayLogoModel::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        fixturesID = parcel.readValue(Int::class.java.classLoader) as? Int
        matchRound = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(teamXX, flags)
        parcel.writeParcelable(awayLogo, flags)
        parcel.writeString(idAwayTeam)
        parcel.writeString(idEvent)
        parcel.writeString(idHomeTeam)
        parcel.writeString(intAwayScore)
        parcel.writeString(intHomeScore)
        parcel.writeString(intRound)
        parcel.writeString(strAwayGoalDetails)
        parcel.writeString(strAwayRedCards)
        parcel.writeString(strAwayTeam)
        parcel.writeString(strAwayYellowCards)
        parcel.writeString(strHomeGoalDetails)
        parcel.writeString(strHomeRedCards)
        parcel.writeString(strHomeTeam)
        parcel.writeString(strHomeYellowCards)
        parcel.writeString(dateEvent)
        parcel.writeString(strTime)
        parcel.writeString(strPostponed)
        parcel.writeValue(fixturesID)
        parcel.writeValue(matchRound)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }

}



