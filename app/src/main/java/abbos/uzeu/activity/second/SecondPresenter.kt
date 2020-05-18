package abbos.uzeu.activity.second

import abbos.uzeu.R
import abbos.uzeu.database.DatabaseDao
import abbos.uzeu.database.DatabaseProvider
import abbos.uzeu.database.model.PhraseModel
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.*

class SecondPresenter(
    val ctx: Context
) : TextToSpeech.OnInitListener {
    interface ViewClick {
        fun onUpdateList(newList: MutableList<PhraseModel>)
    }

    protected var infView: ViewClick = ctx as ViewClick
    protected var textToSpeech: TextToSpeech? = null
    protected var dataBase: DatabaseDao = DatabaseProvider.instance(ctx).databaseDao()

    fun activityLoadlistByCatid(catId: Int) {
        val list = dataBase.loadPhrasesBycatId(catId)
        updateList(list)
    }

    fun activityLoadlistByfav() {
        val list = dataBase.loadPhrasesByfav()
        updateList(list)
    }

    fun activityLoadlistByText(text: String) {
        val list = dataBase.loadPhrasesBysearchText(text)
        updateList(list)
    }

    fun adapterViewClick(viewId: Int, model: PhraseModel) {
        when (viewId) {
            R.id.btn_speak -> {
                adapterSpeak(text = "${model.ru}")
            }
            R.id.btn_share -> {
                adapterShare(text = "${model.uz}\n${model.ru}")
            }
            R.id.btn_fave -> {
                adapterFav(id = model._id)
            }
            R.id.btn_copy -> {
                adapterCopy(text = "${model.ru}\n${model.uz}")
            }
        }
    }

    protected fun print(text: String) {
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show()
    }

    protected fun adapterSpeak(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (textToSpeech != null) {
                textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }

    protected fun adapterFav(id: Int) {
        val fav = if (dataBase.getFav(id).equals("true")) "false" else "true"
        dataBase.setFav(id, fav)
    }

    protected fun adapterShare(text: String) {
        val context = ctx
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.type = "text/plain"
        context.startActivity(Intent.createChooser(shareIntent, "Yuborish yoki saqlash..."))
    }

    protected fun adapterCopy(text: String) {
        val context = ctx
        val clipboard: ClipboardManager? =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("abbos.uzru", "$text")
        clipboard!!.setPrimaryClip(clip)
        print("Nusxa olindi")
    }

    protected fun updateList(newList: MutableList<PhraseModel>) {
        infView.onUpdateList(if (newList != null) newList else mutableListOf())
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS)
            textToSpeech?.setLanguage(Locale("ru_RU"))
    }
}