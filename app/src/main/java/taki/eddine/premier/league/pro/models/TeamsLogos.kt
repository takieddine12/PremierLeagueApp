package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class TeamsLogos(
    @SerializedName("teams")
    val teams: MutableList<Team>?
)