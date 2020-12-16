package taki.eddine.premier.league.pro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.databinding.TeamspickerlayoutBinding
import taki.eddine.premier.league.pro.models.TeamX
import taki.eddine.premier.league.pro.uilisteners.NotificationListener

@InternalCoroutinesApi
class NotificationAdapter(var mutableList: MutableList<TeamX>, var context: Context,var listener : NotificationListener) : RecyclerView.Adapter<NotificationAdapter.NotificationsViewHolder>() {

    class NotificationsViewHolder(var teamPickerLayoutBinding: TeamspickerlayoutBinding) : RecyclerView.ViewHolder(teamPickerLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val binding = DataBindingUtil.inflate<TeamspickerlayoutBinding>(LayoutInflater.from(parent.context),
        R.layout.teamspickerlayout,parent,false)
        return NotificationsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val pickedTeamList = mutableList[position]
        holder.teamPickerLayoutBinding.model = pickedTeamList
        holder.teamPickerLayoutBinding.listener = listener
    }
}