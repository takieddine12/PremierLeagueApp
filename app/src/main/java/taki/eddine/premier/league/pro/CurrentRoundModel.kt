package taki.eddine.premier.league.pro


import com.google.gson.annotations.SerializedName

data class CurrentRoundModel(
    @SerializedName("events")
    val events: List<Event>?
)