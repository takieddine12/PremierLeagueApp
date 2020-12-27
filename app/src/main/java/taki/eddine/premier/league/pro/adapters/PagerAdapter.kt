package taki.eddine.premier.league.pro.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import taki.eddine.premier.league.pro.ui.fragments.*
import taki.eddine.premier.league.pro.R

@ExperimentalCoroutinesApi
@InternalCoroutinesApi class PagerAdapter(fm : FragmentManager, var context : Context) : FragmentStatePagerAdapter(fm){

    private val tabs = 5

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FixturesFragment()
            1 -> StandingsFragment()
            2 -> LiveScoresFragment()
            3 -> TopScorersFragment()
            else -> NewsFragment()
        }
    }

    override fun getCount(): Int {
       return tabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.match)
            1 -> context.getString(R.string.rank)
            2 -> context.getString(R.string.live)
            3 -> context.getString(R.string.scorers)
            else -> context.getString(R.string.news)
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}