package taki.eddine.premier.league.pro.uilisteners

import taki.eddine.premier.league.pro.models.AwayLogoModel
import taki.eddine.premier.league.pro.models.Event
import taki.eddine.premier.league.pro.models.TeamXX


interface RoundListener {
    
    fun getRound(event : Event,teamXX: TeamXX,awayLogoModel: AwayLogoModel)
}