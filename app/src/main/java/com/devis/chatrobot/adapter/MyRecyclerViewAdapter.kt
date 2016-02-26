package com.devis.chatrobot.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devis.chatrobot.Msg
import com.devis.chatrobot.R
import com.devis.chatrobot.ViewHolder.BaseViewHolder
import com.devis.chatrobot.ViewHolder.LeftViewHolder
import com.devis.chatrobot.ViewHolder.RightViewHolder

/**
 * Created by Devis on 16/2/25.
 */
class MyRecyclerViewAdapter(var context: Context, var items: List<Msg>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val LEFT: Int = 1 //接收到的消息
    val RIGHT: Int = 2 //自己发的消息

    fun refreshAdapter(data: List<Msg>){
        this.items = data
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as BaseViewHolder).bindData(items.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            LEFT -> return LeftViewHolder(context, LayoutInflater.from(context).inflate(R.layout.left_text_chat_item, null))
            RIGHT -> return RightViewHolder(LayoutInflater.from(context).inflate(R.layout.right_text_chat_item, null))
        }
        return return RightViewHolder(View(context))
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items.count()
    }

    //判断消息是自己发出的 还是接收到的,以此来显示不同布局
    override fun getItemViewType(position: Int): Int {
        return if (items.get(position).isMe) RIGHT else LEFT
    }
}
