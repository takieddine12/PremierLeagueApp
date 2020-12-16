package taki.eddine.premier.league.pro.imgbinding

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object LiveScoresTeamsLogos {

    @BindingAdapter("teampickimg")
    @JvmStatic
    public fun getPickedTeamUrl(view : ImageView, url : String?){
        Picasso.get().load(url).fit().into(view)
    }

    @BindingAdapter("standingslogo")
    @JvmStatic
    public fun getStandingslogo(image : ImageView, url : String?){
        Picasso.get().load(url).fit().into(image)
    }

    @BindingAdapter("teamsimg")
    @JvmStatic
    public fun getTeamsImg(view : ImageView , url : String?){
        Picasso.get().load(url).fit().into(view)
    }

    ///Fixtures Icons

    @BindingAdapter("homelogo")
    @JvmStatic
    public fun getHomeLogo(view : ImageView,url : String?){
        Picasso.get().load(url).fit().into(view)
    }

    @BindingAdapter("awaylogo")
    @JvmStatic
    public fun getAwayLogo(view : ImageView,url : String?){
        Picasso.get().load(url).fit().into(view)
    }

    ///LiveScore Teams Logos

    @BindingAdapter("livescorehomelogo")
    @JvmStatic
    public fun getLiveScoreHomeLogo(view : ImageView,url : String?){
        Picasso.get().load(url).fit().into(view)
    }

    @BindingAdapter("livescoreawaylogo")
    @JvmStatic
    public fun getLiveScoreAwayLogo(view : ImageView,url : String?){
        Picasso.get().load(url).fit().into(view)
    }
    //---------------------------

    ///Rss Icons

    @BindingAdapter("rssimg")
    @JvmStatic
    public fun getRssImg(view : ImageView,bitmap : Bitmap){
        view.setImageBitmap(bitmap)
    }

    //--------------------------------------------------


    //Event Details Logos
    @BindingAdapter("eventhomelogo")
    @JvmStatic
    public fun getEventHomeLogo(view : ImageView,url : String?){
        Picasso.get().load(url).fit().into(view)
    }

    @BindingAdapter("eventawaylogo")
    @JvmStatic
    public fun getEventAwayLogo(view : ImageView,url : String?){
        Picasso.get().load(url).fit().into(view)
    }

    // Sport Api Call

    @BindingAdapter("sportlogo")
    @JvmStatic
    fun getSportApiLogoUrl(view: ImageView , url : String?){
        Picasso.get().load(url).fit().into(view)
    }
}