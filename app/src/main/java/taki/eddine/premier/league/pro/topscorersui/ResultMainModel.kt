package taki.eddine.premier.league.pro.topscorersui


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "resultTable",indices = [Index(value = ["resultID"],unique = true)])
data class ResultMainModel(
    @SerializedName("goals")
    val goals: String?,
    @SerializedName("player_key")
    val playerKey: String?,
    @SerializedName("player_name")
    val playerName: String?,
    @SerializedName("player_place")
    val playerPlace: String?,
    @SerializedName("team_key")
    val teamKey: String?,
    @SerializedName("team_name")
    val teamName: String?,
    @Embedded var result : ResultX?
) : Parcelable{
    @PrimaryKey(autoGenerate = true)
    var resultID : Int? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(ResultX::class.java.classLoader)
    ) {
        resultID = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun describeContents(): Int {
        return describeContents()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }

    companion object CREATOR : Parcelable.Creator<ResultMainModel> {
        override fun createFromParcel(parcel: Parcel): ResultMainModel {
            return ResultMainModel(parcel)
        }

        override fun newArray(size: Int): Array<ResultMainModel?> {
            return arrayOfNulls(size)
        }
    }


}