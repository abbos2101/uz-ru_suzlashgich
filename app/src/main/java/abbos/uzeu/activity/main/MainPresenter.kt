package abbos.uzeu.activity.main

import abbos.uzeu.activity.second.activity.InfoActivity
import abbos.uzeu.activity.second.activity.FavActivity
import abbos.uzeu.activity.second.activity.PhraseActivity
import abbos.uzeu.activity.second.activity.SearchActivity
import abbos.uzeu.common.*
import abbos.uzeu.database.DatabaseDao
import abbos.uzeu.database.DatabaseProvider
import abbos.uzeu.database.model.CategoryModel
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.*

class MainPresenter {
    interface ViewClick {
        fun onUpdateList(item: Int, newList: List<CategoryModel>)
        fun onItemClick(position: Int, intent: Intent)
        fun onStartActivity(intent: Intent)
    }

    private var infView: ViewClick? = null
    private var ctx: Context? = null
    private var dataBase: DatabaseDao? = null

    constructor(
        context: Context
    ) {
        this.ctx = context
        this.infView = context as ViewClick
        this.dataBase = DatabaseProvider
            .instance(context)
            .databaseDao()
    }

    fun activityClickTV(item: Int) {
        activityLoadList(item)
    }

    fun activityStart() {
        activityLoadList(1)
    }

    fun activityStartActivity(activityId: Int) {
        var intent: Intent? = null
        val context = ctx!!
        when (activityId) {
            activityMain -> {
                intent = Intent(context, MainActivity::class.java)
            }
            activityPhrase -> {
                intent = Intent(context, PhraseActivity::class.java)
                intent.putExtra("$activityTitle", "Phrase")
            }
            activitySearch -> {
                intent = Intent(context, SearchActivity::class.java)
                intent.putExtra("$activityTitle", "Izlash")
            }
            activityFav -> {
                intent = Intent(context, FavActivity::class.java)
                intent.putExtra("$activityTitle", "Tanlanganlar")
            }

            activityInfo -> {
                intent = Intent(context, InfoActivity::class.java)
                intent.putExtra("$activityTitle", "Dastur haqida")
            }
        }
        if (intent != null)
            infView?.onStartActivity(intent)
    }

    fun activityShareApplication(context: Context) {
        val app = context.applicationInfo
        val filePath = app.sourceDir
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "*/*"
        val originalApk = File(filePath)
        try {
            var tempFile = File(context.externalCacheDir.toString() + "/ExtractedApk")
            if (!tempFile.isDirectory()) if (!tempFile.mkdirs()) return
            tempFile = File(
                tempFile.getPath().toString() + "/" + context.getString(app.labelRes)
                    .replace(" ", "")
                    .toLowerCase() + ".apk"
            )
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return
                }
            }
            val input: InputStream = FileInputStream(originalApk)
            val output: OutputStream = FileOutputStream(tempFile)
            val buf = ByteArray(1024)
            var len = 0
            while (input.read(buf).also({ len = it }) > 0) {
                output.write(buf, 0, len)
            }
            input.close()
            output.close()
            println("File copied.")
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile))
            context.startActivity(Intent.createChooser(intent, "Dasturni ulashish"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun activityLoadList(itemPage: Int) {
        val list = dataBase?.loadCategories()
        infView?.onUpdateList(itemPage, if (list != null) list else listOf())
    }

    fun adapterItemClick(position: Int, model: CategoryModel, itemPage: Int) {
        val intent = Intent(ctx!!, PhraseActivity::class.java)
        intent.putExtra(catId, model.cat_id)
        intent.putExtra(activityTitle, if (itemPage == 1) model.cat_uz else model.cat_ru)
        intent.putExtra(catItem, itemPage)
        infView?.onItemClick(position, intent)
    }
}