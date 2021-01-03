package taki.eddine.premier.league.pro.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import dagger.Provides
import org.jetbrains.annotations.NotNull

@Entity(tableName = "StandingBottomTable")
data class BottomStandingModel (
    var strDescriptionEN : String?,
    var strTeam : String?,
    var strTeamBadge : ByteArray?,
    var strTeamBanner : String?,
    var strStadium : ByteArray?,
    var strStadiumLocation : String?,
    var intStadiumCapacity : String?
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NotNull
    var bottomStandingsId : Int?  = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createByteArray(),
        parcel.readString(),
        parcel.createByteArray(),
        parcel.readString(),
        parcel.readString()
    ) {
        bottomStandingsId = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(strDescriptionEN)
        parcel.writeString(strTeam)
        parcel.writeByteArray(strTeamBadge)
        parcel.writeString(strTeamBanner)
        parcel.writeByteArray(strStadium)
        parcel.writeString(strStadiumLocation)
        parcel.writeString(intStadiumCapacity)
        parcel.writeValue(bottomStandingsId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BottomStandingModel> {
        override fun createFromParcel(parcel: Parcel): BottomStandingModel {
            return BottomStandingModel(parcel)
        }

        override fun newArray(size: Int): Array<BottomStandingModel?> {
            return arrayOfNulls(size)
        }
    }


}

