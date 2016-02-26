package com.devis.chatrobot

/**
 * Created by Devis on 16/2/25.
 * 消息实体类
 * text 文本内容
 * code 状态码
 * isMe 是不是我自己发的消息
 */

class Msg(){

    var text: String = "" //文本内容
    var url: String = "" //图片路径
    var code: Int = 0 //状态码
    var isMe: Boolean = false //是不是我自己发的消息

    constructor(text:String, url:String, code:Int, isMe:Boolean):this(){
        this.text = text
        this.url = url
        this.code = code
        this.isMe = isMe
    }
}
