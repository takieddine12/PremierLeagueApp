package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class TeamMatchesModel(
    @SerializedName("events")
    val results: List<Result>?
)