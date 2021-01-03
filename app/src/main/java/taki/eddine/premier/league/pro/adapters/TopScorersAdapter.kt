package taki.eddine.premier.league.pro.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.ui.bottomsheetfragments.TopScorersDetailsBottomSheet
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.databinding.TopscorersrowslayoutBinding
import taki.eddine.premier.league.pro.topscorersui.ResultMainModel
import taki.eddine.premier.league.pro.uilisteners.TopScorersListener

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class TopScorersAdapter(var result: MutableList<ResultMainModel>,
var listener : TopScorersListener) : RecyclerView.Adapter<TopScorersAdapter.ViewHolder>() {

    class ViewHolder(var topScorersRowsLayoutBinding: TopscorersrowslayoutBinding) : RecyclerView.ViewHolder(topScorersRowsLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = DataBindingUtil.inflate<TopscorersrowslayoutBinding>(LayoutInflater.from(parent.context), R.layout.topscorersrowslayout,parent,false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return result.size.minus(101)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = result[position]
        holder.topScorersRowsLayoutBinding.apply {
            model = list
            listener = listener
        }
    }
}