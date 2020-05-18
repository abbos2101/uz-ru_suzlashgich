package abbos.uzeu.activity.second

import abbos.uzeu.adapter.SecondAdapter
import abbos.uzeu.common.*
import abbos.uzeu.database.model.PhraseModel
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_phrase.rv_phrase
import kotlinx.android.synthetic.main.activity_search.*

open class SecondApp() : AppCompatActivity(),
    SecondPresenter.ViewClick {
    private var myPresenter: SecondPresenter? = null
    private var adapter: SecondAdapter? = null
    private var cItemPage: Int = 1
    private var cId: Int = 1

    fun init() {
        myPresenter = SecondPresenter(this)
        adapter = SecondAdapter(mutableListOf())
        rv_phrase.adapter = adapter
    }

    fun getExtraDataSetSupportBar() {
        cId = intent.getIntExtra(catId, 1)
        cItemPage = intent.getIntExtra(catItem, 1)
        supportActionBar?.setTitle(intent.getStringExtra(activityTitle))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter?.setItemPage(cItemPage)
    }

    fun activityFav() {
        myPresenter?.activityLoadlistByfav()
    }

    fun activitySearch() {
        myPresenter?.activityLoadlistByText("%%")
        edt_phrase.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                myPresenter?.activityLoadlistByText("%${KLKconvertor().LotinToKrill(edt_phrase.text.toString())}%")
            }
        })
    }

    fun activityPhrase() {
        myPresenter?.activityLoadlistByCatid(cId)
    }

    override fun onDestroy() {
        super.onDestroy()
        myPresenter = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    override fun onUpdateList(newList: MutableList<PhraseModel>) {
        adapter?.setNewList(newList)
    }
}