package taki.eddine.premier.league.pro.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.ui.bottomsheetfragments.StandingsBottomDialog
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.mvvm.LeagueViewModel
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.databinding.StandinsrowslayoutBinding
import taki.eddine.premier.league.pro.models.Table
import taki.eddine.premier.league.pro.uilisteners.StandingsListener

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class StandingsAdapter(var context : FragmentActivity, private var mergedList : MutableList<Table>) : RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder>() {

    private  var leagueViewModel: LeagueViewModel = ViewModelProvider(context)[LeagueViewModel::class.java]
    class StandingsViewHolder(var binding : StandinsrowslayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder {
      val binding = DataBindingUtil.inflate<StandinsrowslayoutBinding>(LayoutInflater.from(parent.context), R.layout.standinsrowslayout,parent,false)
        return StandingsViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return mergedList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        val list = mergedList[position]
        holder.binding.tablemodel = list

        checkingNulls(list)

        when(list.intRank) {
            1,2,3 -> {
                holder.binding.ranking.text = "\uD83D\uDD3A ${list.intRank}" + "."
            }
            18,19,20 -> {
                holder.binding.ranking.text = "\uD83D\uDD3B ${list.intRank}" + "."
            }
            else -> {
                holder.binding.ranking.text = " ▶ ${list.intRank}" + "."
            }
        }

        holder.binding.listener = object : StandingsListener {
            override fun getClubDetails(table: Table){
                if(Constants.checkConnectivity(context)){
                    val bundle = Bundle()
                    bundle.putString("teamId", table.teamid)
                    val dialog = StandingsBottomDialog()
                    dialog.arguments = bundle
                    if(Constants.dialogCounter == 0){
                        dialog.show(context.supportFragmentManager, dialog.tag)
                        Constants.dialogCounter = 1
                    }
                } else {
                    leagueViewModel.getStandingsBottom().observe(context, Observer {
                        it.map {
                            if(list.name.equals(it.strTeam)){
                                val bundle = Bundle()
                                bundle.putString("description",it.strDescriptionEN)
                                bundle.putString("team",it.strTeam)
                                bundle.putByteArray("teamBadge",it.strTeamBadge)
                                bundle.putString("teambanner",it.strTeamBanner)
                                bundle.putByteArray("stadium",it.strStadium)
                                bundle.putString("stadiumLocation",it.strStadiumLocation)
                                bundle.putString("stadiumCapacity",it.intStadiumCapacity)
                                val dialog = StandingsBottomDialog()
                                dialog.arguments = bundle
                                if(Constants.dialogCounter == 0){
                                    dialog.show(context.supportFragmentManager, dialog.tag)
                                    Constants.dialogCounter = 1
                                }
                            }
                        }
                    })
                }
            }
        }
       }

    }
    private fun checkingNulls(list : Table){
        if(list.name == null){
            list.name = "."
        }
        if(list.played == null){
            list.played = 0
        }
        if(list.goalsDifference == null) {
            list.goalsDifference = 0
        }
        if(list.win == null){
            list.win = 0
        }
        if(list.draw == null){
            list.draw = 0
        }
        if(list.loss == null){
            list.loss = 0
        }
        if(list.total == null){
            list.total = 0
        }
}