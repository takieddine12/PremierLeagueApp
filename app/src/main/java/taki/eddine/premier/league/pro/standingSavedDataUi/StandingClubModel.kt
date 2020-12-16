package taki.eddine.premier.league.pro.standingSavedDataUi


import com.google.gson.annotations.SerializedName

data class StandingClubModel(
    @SerializedName("teams")
    val teams: List<Team>?
)