package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class LiveScoreModel(
    @SerializedName("teams")
    val teams: MutableList<TeamXX>?

)