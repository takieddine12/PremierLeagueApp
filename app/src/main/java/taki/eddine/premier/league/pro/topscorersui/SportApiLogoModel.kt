package taki.eddine.premier.league.pro.topscorersui


import com.google.gson.annotations.SerializedName

data class SportApiLogoModel(
    @SerializedName("result")
    val result: List<ResultX>?

)