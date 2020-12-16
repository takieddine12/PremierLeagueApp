package taki.eddine.premier.league.pro.standingSavedDataUi


import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("strDescriptionEN")
    val strDescriptionEN: String?,
    @SerializedName("strTeam")
    val strTeam: String?,
    @SerializedName("strTeamBadge")
    val strTeamBadge: String?,
    @SerializedName("strTeamBanner")
    val strTeamBanner: String?,
    @SerializedName("strStadium")
    val strStadium : String?,
    @SerializedName("strStadiumThumb")
    val strStadiumThumb : String?,
    @SerializedName("strStadiumLocation")
    val strStadiumLocation : String?,
    @SerializedName("intStadiumCapacity")
    val intStadiumCapacity : String?
)