package taki.eddine.premier.league.pro.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity(tableName = "standingTable",indices = [Index(value = ["tableID"],unique = true)])
class Table(

    @SerializedName("draw")
    var draw: Int?,
    @SerializedName("goalsagainst")
    val goalsAgainst: Int?,
    @SerializedName("goalsdifference")
    var goalsDifference: Int,
    @SerializedName("goalsfor")
    val goalsFor: Int?,
    @SerializedName("loss")
    var loss: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("played")
    var played: Int?,
    @SerializedName("teamid")
    val teamid: String?,
    @SerializedName("total")
    var total: Int?,
    @SerializedName("win")
    var win: Int?,
    @Embedded val teamXX: TeamXX? = null
) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var tableID : Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(TeamXX::class.java.classLoader)
    ) {
        tableID = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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
        parcel.writeParcelable(teamXX, flags)
        parcel.writeValue(tableID)
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