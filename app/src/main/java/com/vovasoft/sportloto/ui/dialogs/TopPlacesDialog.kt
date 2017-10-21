package com.vovasoft.sportloto.ui.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.vovasoft.sportloto.R
import com.vovasoft.sportloto.repository.models.Game
import com.vovasoft.sportloto.repository.models.Winner
import com.vovasoft.sportloto.ui.recycler_adapters.TopPlacesRecyclerAdapter
import kotlinx.android.synthetic.main.dialog_view_top_places.view.*

/***************************************************************************
 * Created by arseniy on 11/10/2017.
 ****************************************************************************/
class TopPlacesDialog(val context: Context, val game: Game) {

    private val topPlacesDialogView: TopPlacesDialogView
    private var dialog: AlertDialog? = null


    init {
        topPlacesDialogView = TopPlacesDialogView(context)
    }


    fun setWinners(winners: List<Winner>) {
        topPlacesDialogView.setWinnersData(winners)
    }


    fun show() {
        val builder = AlertDialog.Builder(context).setView(topPlacesDialogView)
        dialog = builder.create()
        dialog?.show()
    }


    fun dismiss() {
        dialog?.dismiss()
    }



    inner class TopPlacesDialogView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
        : FrameLayout(context, attrs, defStyleAttr) {

        private val adapter = TopPlacesRecyclerAdapter()

        init {
            inflate(context, R.layout.dialog_view_top_places, this)
            setupViews()
        }


        private fun setupViews() {
            closeBtn.setOnClickListener {
                dismiss()
            }

            progressBar.visibility = View.VISIBLE

            val prizeText = "${"%.2f".format(game.prizeAmount)} ETH = ${"$ %.2f".format(game.prizeAmountFiat)}"
            prizeTv.text = prizeText

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }


        fun setWinnersData(winners: List<Winner>) {
            adapter.dataSet = winners.toMutableList()
            progressBar.visibility = View.INVISIBLE
        }

    }

}