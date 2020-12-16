package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("strEvent")
    val strEvent : String?,

    @SerializedName("dateEvent")
    val dateEvent: String?,

    @SerializedName("strTime")
    val strTime: String?

)