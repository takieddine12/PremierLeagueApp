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

@Entity(tableName = "StandingBottomTable",indices = [Index(value = ["bottomStandingsId"],unique = true)])
data class BottomStandingModel (
    var strDescriptionEN : String,
    var strTeam : String,
    var strTeamBadge : ByteArray?,
    var strTeamBanner : String,
    var strStadium : String,
    var strStadiumLocation : String,
    var intStadiumCapacity : String
) {

    @PrimaryKey(autoGenerate = true)
    @NotNull
    var bottomStandingsId : Int?  = null


}

