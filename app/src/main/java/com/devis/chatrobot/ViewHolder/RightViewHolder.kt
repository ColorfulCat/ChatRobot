package com.devis.chatrobot.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.devis.chatrobot.Msg
import com.devis.chatrobot.R

/**
 * Created by Devis on 16/2/25.
 */
class RightViewHolder(val myView: View) : BaseViewHolder(myView) {
    var content: TextView = itemView.findViewById(R.id.content) as TextView

    override fun bindData(message: Msg) {
        content.text = message.text
    }

}