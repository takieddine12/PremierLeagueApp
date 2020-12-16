package taki.eddine.premier.league.pro.livescoresdata


import com.google.gson.annotations.SerializedName

data class v2livescores(
    @SerializedName("events")
    val events: List<EventTwo>?
)