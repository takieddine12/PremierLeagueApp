package taki.eddine.premier.league.pro.ui.viewtypes

import taki.eddine.premier.league.pro.models.Event

 class GeneralItem : ListItem(){

    private var pojoOfJsonArray: Event? = null
      fun getPojoOfJsonArray(): Event? {
        return pojoOfJsonArray
    }

     fun setPojoOfJsonArray(pojoOfJsonArray: Event?) {
        this.pojoOfJsonArray = pojoOfJsonArray
    }

    override fun getType(): Int {
        return TYPE_GENERAL
    }
}