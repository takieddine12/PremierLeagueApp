package taki.eddine.premier.league.pro.livescoresdata


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
) {
    @PrimaryKey(autoGenerate = true)
    var matchID : Int? = null
}