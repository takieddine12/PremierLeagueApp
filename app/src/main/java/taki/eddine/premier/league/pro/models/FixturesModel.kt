package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class FixturesModel(
    @SerializedName("events")
    val events: MutableList<Event>?
)