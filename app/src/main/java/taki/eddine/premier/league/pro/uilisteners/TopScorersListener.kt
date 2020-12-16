package taki.eddine.premier.league.pro.uilisteners

import taki.eddine.premier.league.pro.topscorersui.ResultMainModel
import taki.eddine.premier.league.pro.topscorersui.ResultX

interface TopScorersListener {

    fun topScorers(resultX: ResultMainModel)
}