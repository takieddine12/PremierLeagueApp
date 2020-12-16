package taki.eddine.premier.league.pro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import taki.eddine.premier.league.pro.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.databinding.LivescoresrowslayoutBinding
import taki.eddine.premier.league.pro.livescoresdata.EventTwo

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LiveScoresAdapter(var context : Context, var list : MutableList<EventTwo>) :
   RecyclerView.Adapter<LiveScoresAdapter.LiveScoresViewHolder>() {


    class LiveScoresViewHolder(var liveScoresRowsLayoutBindingImpl: LivescoresrowslayoutBinding) : RecyclerView.ViewHolder(liveScoresRowsLayoutBindingImpl.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveScoresViewHolder {
        val binding = DataBindingUtil.inflate<LivescoresrowslayoutBinding>(
            LayoutInflater.from(context), R.layout.livescoresrowslayout, parent, false)
        return LiveScoresViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LiveScoresViewHolder, position: Int) {
        val liveScoresModels = list[position]
        holder.liveScoresRowsLayoutBindingImpl.matchModel = liveScoresModels

            }
        }



