package abbos.uzeu.activity.main

import abbos.uzeu.R
import abbos.uzeu.adapter.MainAdapter
import abbos.uzeu.common.*
import abbos.uzeu.database.model.CategoryModel
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    MainPresenter.ViewClick {
    private var toggle: ActionBarDrawerToggle? = null
    private var mainPresenter: MainPresenter? = null
    private var adapter: MainAdapter? = null
    private var item: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setEvent()
    }

    private fun init() {
        mainPresenter = MainPresenter(this)
        adapter = MainAdapter()
        rv_main.adapter = adapter

        toggle = ActionBarDrawerToggle(
            this, dl_main,
            R.string.Open,
            R.string.Close
        )
        dl_main.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setEvent() {
        mainPresenter?.activityStart()
        tv_uzru.setOnClickListener {
            adapter?.setItemPage(1)
            mainPresenter?.activityClickTV(1)
        }
        tv_ruuz.setOnClickListener {
            adapter?.setItemPage(2)
            mainPresenter?.activityClickTV(2)
        }
        ll_search.setOnClickListener { mainPresenter?.activityStartActivity(activitySearch) }
        ll_fav.setOnClickListener { mainPresenter?.activityStartActivity(activityFav) }
        ll_info.setOnClickListener { mainPresenter?.activityStartActivity(activityInfo) }
        ll_share.setOnClickListener { mainPresenter?.activityShareApplication(this) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle!!.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter = null
    }

    override fun onUpdateList(item: Int, newList: List<CategoryModel>) {
        this.item = item
        v_uzru.visibility = View.GONE
        v_ruuz.visibility = View.GONE
        when (item) {
            1 -> v_uzru.visibility = View.VISIBLE
            2 -> v_ruuz.visibility = View.VISIBLE
        }
        adapter?.setNewList(newList)
    }

    override fun onItemClick(position: Int, intent: Intent) {
        startActivity(intent)
    }

    override fun onStartActivity(intent: Intent) {
        startActivity(intent)
    }
}