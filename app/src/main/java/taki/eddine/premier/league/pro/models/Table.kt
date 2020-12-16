package taki.eddine.premier.league.pro.models


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
){
    @PrimaryKey(autoGenerate = true)
    var tableID : Int? = null
}