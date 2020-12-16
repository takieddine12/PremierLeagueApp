package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class TeamX(
    @SerializedName("idTeam")
    val idTeam: Int?,
    @SerializedName("strTeam")
    val strTeam: String?,
    @SerializedName("strTeamBadge")
    val strTeamBadge : String?
)