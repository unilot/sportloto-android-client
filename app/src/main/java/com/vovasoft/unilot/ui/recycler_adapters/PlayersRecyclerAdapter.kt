package com.vovasoft.unilot.ui.recycler_adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vovasoft.unilot.R
import com.vovasoft.unilot.repository.models.entities.Wallet
import com.vovasoft.unilot.repository.models.pure.Player
import com.vovasoft.unilot.ui.view_holders.PlayersViewHolder

/***************************************************************************
 * Created by arseniy on 14/10/2017.
 ****************************************************************************/
class PlayersRecyclerAdapter : RecyclerView.Adapter<PlayersViewHolder>() {

    enum class ViewType(val value: Int) {
        WHITE(0), GRAY(1), HIGHLIGHTED(2);

        companion object {
            fun from(findValue: Int) : ViewType {
                return if (findValue % 2 == 0) {
                    WHITE
                } else {
                    GRAY
                }
            }
        }
    }


    var dataSet = mutableListOf<Player>()
        set(value) {
            field.clear()
            field.addAll(value)
            dataSet.sortByDescending { wallets.map { it.number?.toLowerCase() }.contains(it.wallet?.toLowerCase()) }
            notifyDataSetChanged()
        }


    var wallets = mutableListOf<Wallet>()
        set(value) {
            field.clear()
            field.addAll(value)
            dataSet.sortByDescending { wallets.map { it.number?.toLowerCase() }.contains(it.wallet?.toLowerCase()) }
            notifyDataSetChanged()
        }



    override fun getItemViewType(position: Int): Int {
        var viewType = ViewType.from(position).value

        wallets.forEach {
            if (it.number?.toLowerCase() == dataSet[position].wallet?.toLowerCase()) {
                viewType = ViewType.HIGHLIGHTED.value
            }
        }

        return viewType
    }


    override fun getItemCount(): Int {
        return dataSet.size
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlayersViewHolder {
        var view: View? = null

        when(viewType) {
            ViewType.WHITE.value -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_players_white, parent,false)
            }
            ViewType.GRAY.value -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_players_gray, parent,false)
            }
            ViewType.HIGHLIGHTED.value -> {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_players_highlighted, parent,false)
            }
        }

        return PlayersViewHolder(view)
    }


    override fun onBindViewHolder(holder: PlayersViewHolder?, position: Int) {
        holder?.setData(dataSet[position])
    }

}