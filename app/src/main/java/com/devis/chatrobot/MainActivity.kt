package com.devis.chatrobot

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.devis.chatrobot.adapter.MyRecyclerViewAdapter
import org.jetbrains.anko.*
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //初始化控件,用lazy方式滞后,在第一次调用该成员时进行线程安全的初始化操作
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerview) as RecyclerView }
    private val sendButton: LinearLayout by lazy { findViewById(R.id.input_text_send_layout) as LinearLayout }
    private val inputEditText: EditText by lazy { findViewById(R.id.input_text_send_edittext) as EditText }

    //图灵机器人API  获取KEY请访问 : http://www.tuling123.com/
    private val BASE_URL: String = "http://www.tuling123.com/openapi/api"
    private val API_KEY: String = "4f3664efdfacd7f7968f72d2dd594113"
    private var USER_ID: String = "default"

    private var messages = ArrayList<Msg>()

    private var adapter: MyRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initView()
    }

    fun initData() {
        val TelephonyMgr: TelephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager;
        USER_ID = TelephonyMgr.getDeviceId();
        Log.e(javaClass.simpleName, "USER_ID = $USER_ID")
    }

    fun initView() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //侧滑菜单
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        //发送按钮
        sendButton.setOnClickListener {
            sendMessage(inputEditText.text.toString())
        }

        //欢迎语
        var message: Msg = Msg("嗨!你好,我是小花猫,很高兴认识你~", "", 0, false)
        messages.add(message)

        //聊天内容列表
        adapter = MyRecyclerViewAdapter(this, messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    /**
     * 发送消息请求
     */
    fun sendMessage(content: String) {
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入您要说的话", Toast.LENGTH_SHORT).show()
            return;
        }
        var message: Msg = Msg(content, "", 0, true)
        messages.add(message)
        adapter!!.refreshAdapter(messages)
        recyclerView.scrollToPosition(messages.size - 1)

        //开始请求API
        async() {
            //拼接请求
            val url: String = "$BASE_URL?key=$API_KEY&info=${URLEncoder.encode(content)}&userid=$USER_ID"
            //发送请求,获取返回的字符串
            val data = URL(url).readText()
            Log.e(javaClass.simpleName, "url = $url")
            Log.e(javaClass.simpleName, "data = $data")

            //JSON解析
            var jsonObject: JSONObject = JSONObject(data)
            var text: String = jsonObject.optString("text")
            Log.e(javaClass.simpleName, "text = $text")
            var imgUrl: String = jsonObject.optString("url")
            Log.e(javaClass.simpleName, "imgUrl = $imgUrl")
            //加入新消息
            var message: Msg = Msg(text, imgUrl, 0, false)
            messages.add(message)
            //返回主线程更新UI
            uiThread {
                adapter!!.refreshAdapter(messages)
                recyclerView.scrollToPosition(messages.size - 1)
                inputEditText.setText("")
            }
        }
    }


    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {

            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
