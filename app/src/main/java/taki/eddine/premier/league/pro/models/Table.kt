package taki.eddine.premier.league.pro.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull


@Entity(tableName = "standingTable")
class Table(

    @SerializedName("strForm")
    var strForm : String?,
    @SerializedName("strTeamBadge")
    var strTeamBadge : String?,
    @SerializedName("intRank")
    var intRank : Int?,
    @SerializedName("intDraw")
    var draw: Int?,
    @SerializedName("intGoalsAgainst")
    val goalsAgainst: Int?,
    @SerializedName("intGoalDifference")
    var goalsDifference: Int,
    @SerializedName("intGoalsFor")
    val goalsFor: Int?,
    @SerializedName("intLoss")
    var loss: Int?,
    @SerializedName("strTeam")
    var name: String?,
    @SerializedName("intPlayed")
    var played: Int?,
    @SerializedName("idTeam")
    val teamid: String?,
    @SerializedName("intPoints")
    var total: Int?,
    @SerializedName("intWin")
    var win: Int?
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NotNull
    var tableID : Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strForm)
        parcel.writeString(strTeamBadge)
        parcel.writeValue(intRank)
        parcel.writeValue(draw)
        parcel.writeValue(goalsAgainst)
        parcel.writeInt(goalsDifference)
        parcel.writeValue(goalsFor)
        parcel.writeValue(loss)
        parcel.writeString(name)
        parcel.writeValue(played)
        parcel.writeString(teamid)
        parcel.writeValue(total)
        parcel.writeValue(win)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Table> {
        override fun createFromParcel(parcel: Parcel): Table {
            return Table(parcel)
        }

        override fun newArray(size: Int): Array<Table?> {
            return arrayOfNulls(size)
        }
    }

}