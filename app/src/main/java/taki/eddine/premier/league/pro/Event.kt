package taki.eddine.premier.league.pro


import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("dateEvent")
    val dateEvent: String?,
    @SerializedName("intRound")
    val intRound: String?,
    @SerializedName("intHomeScore")
    val intHomeScore : String?

)