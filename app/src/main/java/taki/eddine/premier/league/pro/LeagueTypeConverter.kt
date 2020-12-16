package taki.eddine.premier.league.pro

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class LeagueTypeConverter {

    // TODO : From Bitmap To Bytes
    @TypeConverter
    fun fromBitmapToBytes(bitmap : Bitmap) : ByteArray{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

   // TODO : From Bytes To Bitmap
    @TypeConverter
    fun fromBytesToBitmap(byteArray: ByteArray) : Bitmap{
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }
}