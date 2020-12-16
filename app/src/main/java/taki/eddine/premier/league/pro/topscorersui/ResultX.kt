package taki.eddine.premier.league.pro.topscorersui


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ResultX(

    @SerializedName("team_logo")
    val teamLogo: String?

)