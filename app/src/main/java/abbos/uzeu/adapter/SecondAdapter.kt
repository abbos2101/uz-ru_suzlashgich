package abbos.uzeu.adapter

import abbos.uzeu.R
import abbos.uzeu.activity.second.SecondPresenter
import abbos.uzeu.database.model.PhraseModel
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class SecondAdapter(
    private var list: MutableList<PhraseModel>
) : RecyclerView.Adapter<SecondAdapter.ViewHolder>() {
    class ViewHolder : RecyclerView.ViewHolder {
        internal var tv_header: TextView? = null
        internal var tv_translate: TextView? = null
        internal var btn_speak: ImageButton? = null
        internal var btn_share: ImageButton? = null
        internal var btn_fave: ImageButton? = null
        internal var btn_copy: ImageButton? = null

        constructor(itemView: View) : super(itemView) {
            this.tv_header = itemView.findViewById(R.id.tv_header)
            this.tv_translate = itemView.findViewById(R.id.tv_translate)
            this.btn_speak = itemView.findViewById(R.id.btn_speak)
            this.btn_share = itemView.findViewById(R.id.btn_share)
            this.btn_fave = itemView.findViewById(R.id.btn_fave)
            this.btn_copy = itemView.findViewById(R.id.btn_copy)
        }
    }

    private var ctx: Context? = null
    private var myPresenter: SecondPresenter? = null
    private var itemPage = 1

    fun setNewList(newList: MutableList<PhraseModel>) {
        this.list = newList
        this.notifyDataSetChanged()
    }

    fun setItemPage(newItemPage: Int) {
        this.itemPage = newItemPage
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.ctx = parent.context
        this.myPresenter = SecondPresenter(ctx!!)

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_phrase, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list!!.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list != null) {
            val model = list!![position]
            holder.tv_header?.setText(if (itemPage == 1) model.uz else model.ru)
            holder.tv_translate?.setText(if (itemPage == 1) model.ru else model.uz)
            holder.btn_speak?.setOnClickListener { myPresenter?.adapterViewClick(it.id, model) }
            holder.btn_share?.setOnClickListener { myPresenter?.adapterViewClick(it.id, model) }
            holder.btn_fave?.setOnClickListener {
                myPresenter?.adapterViewClick(it.id, model)
                var notFav = if (model.is_fav.equals("true")) "false" else "true"
                model.is_fav = notFav
                holder.btn_fave?.setImageResource(
                    if (model.is_fav.equals("false")) R.drawable.ic_star_off
                    else R.drawable.ic_star_on
                )
            }
            holder.btn_copy?.setOnClickListener { myPresenter?.adapterViewClick(it.id, model) }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                holder.btn_speak?.visibility = View.GONE
            }
            holder.btn_fave?.setImageResource(
                if (model.is_fav.equals("false")) R.drawable.ic_star_off
                else R.drawable.ic_star_on
            )
        }
    }
}