package taki.eddine.premier.league.pro.models


import com.google.gson.annotations.SerializedName

data class StandingsModel(
    @SerializedName("table")
    val table: MutableList<Table>?
)