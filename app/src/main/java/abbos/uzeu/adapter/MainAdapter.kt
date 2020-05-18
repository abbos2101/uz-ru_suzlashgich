package abbos.uzeu.adapter

import abbos.uzeu.activity.main.MainPresenter
import abbos.uzeu.R
import abbos.uzeu.database.model.CategoryModel
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream


class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder : RecyclerView.ViewHolder {
        internal var img_item: ImageView? = null
        internal var tv_item: TextView? = null
        internal var ll_item: LinearLayout? = null

        constructor(itemView: View) : super(itemView) {
            this.img_item = itemView.findViewById(R.id.img_item)
            this.tv_item = itemView.findViewById(R.id.tv_item)
            this.ll_item = itemView.findViewById(R.id.ll_item)
        }
    }

    private var list: List<CategoryModel>? = null
    private var ctx: Context? = null
    private var mainPresenter: MainPresenter? = null
    private var itemPage = 1

    fun setNewList(newList: List<CategoryModel>) {
        this.list = newList
        this.notifyDataSetChanged()
    }

    fun setItemPage(newItemPage: Int) {
        this.itemPage = newItemPage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.ctx = parent.context
        this.mainPresenter = MainPresenter(ctx!!)

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list != null) {
            val model = list!![position]
            val imgPath = "images/${list!![position].img}.png"

            holder.img_item?.setImageDrawable(loadDrawableFromAssets(ctx!!, imgPath))
            holder.tv_item?.text = if (itemPage == 1) model.cat_uz else model.cat_ru
            holder.ll_item?.setOnClickListener {
                mainPresenter?.adapterItemClick(position, model, itemPage)
            }
        }
    }

    private fun loadDrawableFromAssets(context: Context, path: String?): Drawable? {
        var stream: InputStream? = null
        try {
            stream = context.getAssets().open(path!!)
            return Drawable.createFromStream(stream, null)
        } catch (ignored: Exception) {
        } finally {
            try {
                if (stream != null) {
                    stream.close()
                }
            } catch (ignored: Exception) {
            }
        }
        return null
    }
}