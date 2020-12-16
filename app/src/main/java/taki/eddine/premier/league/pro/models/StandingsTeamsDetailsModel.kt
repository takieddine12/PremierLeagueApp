package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class StandingsTeamsDetailsModel(
    @SerializedName("teams")
    val teams: List<TeamXXX>?
)

