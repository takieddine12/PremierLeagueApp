package taki.eddine.premier.league.pro.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "NewsTable")
data class NewsModel(

     var newsBanner: Bitmap?,
     var newsTitle: String?,
     var newsDescription: String?,
     var newsDate: String?,
     var link: String?
) : Parcelable {
     @PrimaryKey(autoGenerate = true)
     var newsID : Int? = null

     constructor(parcel: Parcel) : this(
          parcel.readParcelable(Bitmap::class.java.classLoader),
          parcel.readString(),
          parcel.readString(),
          parcel.readString(),
          parcel.readString()
     ) {
          newsID = parcel.readValue(Int::class.java.classLoader) as? Int
     }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
          parcel.writeParcelable(newsBanner, flags)
          parcel.writeString(newsTitle)
          parcel.writeString(newsDescription)
          parcel.writeString(newsDate)
          parcel.writeString(link)
          parcel.writeValue(newsID)
     }

     override fun describeContents(): Int {
          return 0
     }

     companion object CREATOR : Parcelable.Creator<NewsModel> {
          override fun createFromParcel(parcel: Parcel): NewsModel {
               return NewsModel(parcel)
          }

          override fun newArray(size: Int): Array<NewsModel?> {
               return arrayOfNulls(size)
          }
     }
}