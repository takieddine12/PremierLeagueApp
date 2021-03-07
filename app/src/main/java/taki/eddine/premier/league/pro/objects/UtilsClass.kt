package taki.eddine.premier.league.pro.objects

import android.annotation.SuppressLint
import androidx.datastore.preferences.core.intPreferencesKey
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object UtilsClass {

     val ROUND_PREFERENCES = intPreferencesKey("roundPrefs")

    fun convertHour(strTime : String ) : String{
        val utcFormat = SimpleDateFormat("HH:mm", Locale.UK)
        utcFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = utcFormat.parse(strTime)

        val deviceFormat = SimpleDateFormat("HH:mm")
        deviceFormat.timeZone = TimeZone.getDefault() //Device timezone
        return deviceFormat.format(date!!)
    }
    fun convertDate(strDate : String) : String {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        utcFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = utcFormat.parse(strDate)

        val deviceFormat = SimpleDateFormat("yyyy-MM-dd")
        deviceFormat.timeZone = TimeZone.getDefault() //Device timezone

        return deviceFormat.format(date)
    }
    fun convertUpdatedDate(updatedDate : String) : String {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
        utcFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = utcFormat.parse(updatedDate)

        val deviceFormat = SimpleDateFormat("yyyy-MM-dd")
        deviceFormat.timeZone = TimeZone.getDefault() //Device timezone

        return deviceFormat.format(date)
    }

}