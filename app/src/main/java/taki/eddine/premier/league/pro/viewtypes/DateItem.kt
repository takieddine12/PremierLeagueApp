package taki.eddine.premier.league.pro.viewtypes


 class DateItem : ListItem(){

    private var date: String? = null

    open fun getDate(): String? {
        return date
    }

    open fun setDate(date: String?) {
        this.date = date
    }

    override fun getType(): Int {
        return TYPE_DATE
    }
}