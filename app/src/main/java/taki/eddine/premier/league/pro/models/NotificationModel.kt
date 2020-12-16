package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @SerializedName("teams")
    val teams: MutableList<TeamX>?
)