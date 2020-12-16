package taki.eddine.premier.league.pro.topscorersui


import com.google.gson.annotations.SerializedName

data class TopScorersPlayersDetails(
    @SerializedName("result")
    val result: List<PlayerResult>?
)