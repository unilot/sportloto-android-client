package com.vovasoft.sportloto.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vovasoft.sportloto.R
import com.vovasoft.sportloto.view_models.GamesVM
import kotlinx.android.synthetic.main.fragment_game_daily.*

/***************************************************************************
 * Created by arseniy on 16/09/2017.
 ****************************************************************************/
class GameDailyFragment : BaseFragment() {


    private val gamesVM: GamesVM
        get() = ViewModelProviders.of(activity).get(GamesVM::class.java)


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_game_daily, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }


    private fun observeData() {
        gamesVM.getDailyGame().observe(this, Observer { game ->
            game?.let {
                Log.e("observeData", game.toString())
            }
        })
    }


    private fun setupViews() {

    }
}
