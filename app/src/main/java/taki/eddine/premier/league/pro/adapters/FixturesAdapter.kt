package taki.eddine.premier.league.pro.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.util.Pair
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.ui.appui.EventDetailsActivity
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.databinding.DaterowlayoutBinding
import taki.eddine.premier.league.pro.databinding.FixturesrowslayoutBinding
import taki.eddine.premier.league.pro.models.AwayLogoModel
import taki.eddine.premier.league.pro.models.Event
import taki.eddine.premier.league.pro.models.TeamXX
import taki.eddine.premier.league.pro.uilisteners.RoundListener
import taki.eddine.premier.league.pro.ui.viewtypes.DateItem
import taki.eddine.premier.league.pro.ui.viewtypes.GeneralItem
import taki.eddine.premier.league.pro.ui.viewtypes.ListItem


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class FixturesAdapter  constructor(var context: FragmentActivity, var fixturesList : MutableList<ListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class DataHolder(var fixturesRowsLayoutBinding: FixturesrowslayoutBinding) : RecyclerView.ViewHolder(fixturesRowsLayoutBinding.root)
    class DateTimeHolder(var dateRowLayoutBinding: DaterowlayoutBinding) : RecyclerView.ViewHolder(dateRowLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when(viewType){
            ListItem.TYPE_GENERAL ->{
                val dataBinding = DataBindingUtil.inflate<FixturesrowslayoutBinding>(LayoutInflater.from(context),
                R.layout.fixturesrowslayout,parent,false)
                viewHolder = DataHolder(dataBinding)
            }
            ListItem.TYPE_DATE -> {
                val dateTimeBinding = DataBindingUtil.inflate<DaterowlayoutBinding>(LayoutInflater.from(context),
                    R.layout.daterowlayout,parent,false)
                viewHolder = DateTimeHolder(dateTimeBinding)
            }
        }
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return fixturesList.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = fixturesList[position]
        when(holder.itemViewType){
            ListItem.TYPE_GENERAL ->{
                val generalValues = list as GeneralItem
                val dataViewHolder = holder as DataHolder

                if(generalValues.getPojoOfJsonArray()?.strPostponed == "yes"){
                    generalValues.getPojoOfJsonArray()!!.strPostponed = "Scheduled"
                }
                dataViewHolder.fixturesRowsLayoutBinding.fixturesmodel = generalValues.getPojoOfJsonArray()
                 dataViewHolder.fixturesRowsLayoutBinding.roundlistene = object  : RoundListener{
                     override fun getRound(event: Event, teamXX: TeamXX, awayLogoModel: AwayLogoModel) {
                         if(Constants.checkConnectivity(context)){
                             Intent(context, EventDetailsActivity::class.java).apply {
                                 val pair1 = Pair<View, String>(dataViewHolder.fixturesRowsLayoutBinding.fixtureshomelogo, context.getString(R.string.transitionhomeicon))
                                 val pair2 = Pair<View, String>(dataViewHolder.fixturesRowsLayoutBinding.fixturesawaylogo, context.getString(R.string.transitionawayicon))
                                 val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(context, *arrayOf(pair1, pair2))

                                 putExtra("event",event)
                                 putExtra("teamxx",teamXX)
                                 putExtra("awaylogomodel",awayLogoModel)
                                 context.startActivity(this, activityOptionsCompat.toBundle())
                             }
                         }  else {
                             Intent(context, EventDetailsActivity::class.java).apply {

                                 val pair1 = Pair<View, String>(holder.fixturesRowsLayoutBinding.fixtureshomelogo, context.getString(R.string.transitionhomeicon))
                                 val pair2 = Pair<View, String>(holder.fixturesRowsLayoutBinding.fixturesawaylogo, context.getString(R.string.transitionawayicon))
                                 val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(context, *arrayOf(pair1, pair2))

                                 putExtra("event",event)
                                 putExtra("teamxx",teamXX)
                                 putExtra("awaylogomodel",awayLogoModel)
                                 context.startActivity(this, activityOptionsCompat.toBundle())
                            }
                         }
                     }
                 }


            }
            ListItem.TYPE_DATE -> {
                val dateTimeValues = list as DateItem
                val dateViewHolder = holder as DateTimeHolder
                dateViewHolder.dateRowLayoutBinding.dateEvent = dateTimeValues.getDate()
            }
        }

    }
    override fun getItemViewType(position: Int): Int {
        return fixturesList[position].getType()
    }

}



