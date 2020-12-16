package taki.eddine.premier.league.pro.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import taki.eddine.premier.league.pro.fragments.FixtureHomeTeamFragment
import taki.eddine.premier.league.pro.fragments.FixturesAwayTeamFragment

class FixtureViewPagerAdapter(fm : FragmentActivity) : FragmentStateAdapter(fm) {

    private val tabs = 2
    override fun getItemCount(): Int {
        return tabs
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0 )
            FixtureHomeTeamFragment()
        else
            FixturesAwayTeamFragment()
    }
}