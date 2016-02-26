package com.devis.chatrobot.ViewHolder

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.devis.chatrobot.MainActivity
import com.devis.chatrobot.Msg
import com.devis.chatrobot.R
import com.devis.chatrobot.WebViewActivity
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor

/**
 * Created by Devis on 16/2/25.
 */
class LeftViewHolder(val myContext: Context, val myView: View) : BaseViewHolder(myView) {
    var content: TextView = itemView.findViewById(R.id.content) as TextView

    override fun bindData(message: Msg) {
        content.text = message.text
        if (!TextUtils.isEmpty(message.url)) {
            content.text = message.text + "【点此查看链接内容】"
            content.textColor = Color.BLUE
            content.onClick {
                //跳转至系统浏览器查看url内容
                val intent = Intent(myContext, WebViewActivity::class.java)
                intent.putExtra("url", message.url)
                intent.putExtra("title", message.text)
                myContext.startActivity(intent)
            }
        } else {
            content.onClick {}
            content.textColor = Color.BLACK
        }
    }

}