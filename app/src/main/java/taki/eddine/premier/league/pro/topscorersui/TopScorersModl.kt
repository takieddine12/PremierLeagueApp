package taki.eddine.premier.league.pro.topscorersui


import com.google.gson.annotations.SerializedName

data class TopScorersModl(
    @SerializedName("result")
    val result: MutableList<ResultMainModel>?

)