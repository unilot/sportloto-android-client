package com.vovasoft.sportloto.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vovasoft.sportloto.R
import com.vovasoft.sportloto.ui.adapters.MainPagerAdapter
import com.vovasoft.sportloto.view_models.GamesVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main_pager.*





/***************************************************************************
 * Created by arseniy on 15/09/2017.
 ****************************************************************************/
class MainPagerFragment : BaseFragment() {

    private val gamesVM: GamesVM
        get() = ViewModelProviders.of(activity).get(GamesVM::class.java)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main_pager, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawerBtn.setOnClickListener { activity.drawerLayout.openDrawer(GravityCompat.START) }
        setupPager()
    }


    private fun setupPager() {
        val pagerAdapter = MainPagerAdapter(childFragmentManager)
        pager.adapter = pagerAdapter

        tabs.setupWithViewPager(pager)
        tabs.getTabAt(0)?.setText(R.string.daily)?.setIcon(R.drawable.ic_day_gray)
        tabs.getTabAt(1)?.setText(R.string.weekly)?.setIcon(R.drawable.ic_week_gray)
        tabs.getTabAt(2)?.setText(R.string.monthly_bonus)?.setIcon(R.drawable.ic_month_gray)
        tabs.getTabAt(3)?.setText(R.string.profile)?.setIcon(R.drawable.ic_profile_gray)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val x = ((pager.width * position + positionOffsetPixels) * computeFactor()).toInt()
                scrollView.scrollTo(x, 0)
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            private fun computeFactor(): Float {
                return (backgroundImg.width - pager.width) / (pager.width * (pager.adapter.count - 1)).toFloat()
            }
        })

    }
}
