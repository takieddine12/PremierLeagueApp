package taki.eddine.premier.league.pro.uilisteners

import taki.eddine.premier.league.pro.models.Table

interface StandingsListener {
     fun getClubDetails(table : Table)
}