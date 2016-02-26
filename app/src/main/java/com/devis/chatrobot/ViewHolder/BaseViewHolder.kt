package com.devis.chatrobot.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.devis.chatrobot.Msg

/**
 * Created by Devis on 16/2/25.
 */
abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindData(msg: Msg)

}