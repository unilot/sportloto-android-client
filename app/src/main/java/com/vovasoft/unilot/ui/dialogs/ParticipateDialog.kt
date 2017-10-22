package com.vovasoft.unilot.ui.dialogs

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.Toast
import com.vovasoft.unilot.R
import com.vovasoft.unilot.repository.models.Game
import kotlinx.android.synthetic.main.dialog_view_participate.view.*


/***************************************************************************
 * Created by arseniy on 12/10/2017.
 ****************************************************************************/
class ParticipateDialog(val context: Context, val game: Game) {

    private val participateDialogView: ParticipateDialogView
    private var dialog: AlertDialog? = null


    init {
        participateDialogView = ParticipateDialogView(context)
    }


    fun show() {
        val builder = AlertDialog.Builder(context).setView(participateDialogView)
        dialog = builder.create()
        dialog?.show()
    }


    fun dismiss() {
        dialog?.dismiss()
    }



    inner class ParticipateDialogView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
        : FrameLayout(context, attrs, defStyleAttr) {

        private var countDown: CountDownTimer? = null

        init {
            inflate(context, R.layout.dialog_view_participate, this)
            setupViews()
        }


        private fun setupViews() {
            closeBtn.setOnClickListener {
                dismiss()
            }

            copyBtn.setOnClickListener {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("wallet", walletTv.text)
                clipboard.primaryClip = clip
                Toast.makeText(context, R.string.wallet_number_has_been_copied, Toast.LENGTH_SHORT).show()
            }

            when (game.type) {
                Game.Type.DAILY.value -> {
                    var timeLeft = "00:00:00"
                    countDown = object : CountDownTimer(game.endTime() - System.currentTimeMillis(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val seconds = (millisUntilFinished / 1000) % 60
                            val minutes = (millisUntilFinished / (1000 * 60)) % 60
                            val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24

                            timeLeft = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                            timeTv.text = timeLeft
                        }

                        override fun onFinish() { }
                    }
                    timeTv.text = timeLeft
                    titleTv.setText(R.string.daily_lottery_unilot)
                    countDown?.start()

                }
                Game.Type.WEEKLY.value -> {
                    var timeLeft = "00:00:00"
                    countDown = object : CountDownTimer(game.endTime() - System.currentTimeMillis(), 60000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val minutes = (millisUntilFinished / (1000 * 60)) % 60
                            val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
                            val days = (millisUntilFinished / (1000 * 60 * 60 * 24))

                            timeLeft = String.format("%02d:%02d:%02d", days, hours, minutes)
                            timeTv.text = timeLeft
                        }

                        override fun onFinish() { }
                    }
                    timeTv.text = timeLeft
                    titleTv.setText(R.string.weekly_lottery_unilot)
                    countDown?.start()

                }
            }
        }


        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            countDown?.cancel()
        }

    }

}