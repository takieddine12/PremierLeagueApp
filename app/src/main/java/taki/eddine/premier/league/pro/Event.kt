package taki.eddine.premier.league.pro


import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("dateEvent")
    val dateEvent: String? = null ,
    @SerializedName("intRound")
    val intRound: Int? = null ,
    @SerializedName("intHomeScore")
    val intHomeScore : String? = null

)