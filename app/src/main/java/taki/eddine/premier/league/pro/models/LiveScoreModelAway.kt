package taki.eddine.premier.league.pro.models

import com.google.gson.annotations.SerializedName

data class LiveScoreModelAway(
    @SerializedName("teams")
    val teams: MutableList<AwayLogoModel>?
)