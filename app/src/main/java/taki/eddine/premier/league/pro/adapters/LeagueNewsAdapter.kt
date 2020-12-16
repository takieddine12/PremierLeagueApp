package taki.eddine.premier.league.pro.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.databinding.NewsrowlayoutBinding
import taki.eddine.premier.league.pro.models.NewsModel
import taki.eddine.premier.league.pro.uilisteners.RssListener

class LeagueNewsAdapter(var context : Context , var list : MutableList<NewsModel> , var listener : RssListener) : RecyclerView.Adapter<LeagueNewsAdapter.NewsHolder>() {

    class NewsHolder(var newsrowlayoutBinding: NewsrowlayoutBinding) : RecyclerView.ViewHolder(newsrowlayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = DataBindingUtil.inflate<NewsrowlayoutBinding>(LayoutInflater.from(context), R.layout.newsrowlayout,parent,false)
        return NewsHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val list = list[position]
        holder.newsrowlayoutBinding.newsModel = list
        holder.newsrowlayoutBinding.listener= listener
    }
}