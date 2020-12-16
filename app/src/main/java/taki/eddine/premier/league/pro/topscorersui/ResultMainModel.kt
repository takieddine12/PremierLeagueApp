package taki.eddine.premier.league.pro.topscorersui


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
    @Embedded var result : ResultX
) {
    @PrimaryKey(autoGenerate = true)
    var resultID : Int? = null
}