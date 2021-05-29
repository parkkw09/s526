package app.peterkwp.s526.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import app.peterkwp.s526.R
import app.peterkwp.s526.databinding.ItemBookBinding
import app.peterkwp.s526.domain.model.Book
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions

class BookmarkAdapter(
    private val glideManager: RequestManager,
    private val func : (Book) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val bookList: MutableList<Book> = mutableListOf()

    fun addAllData(list: List<Book>) {
        bookList.addAll(list)
        notifyDataSetChanged()
    }

    fun updateData(index: Int, item: Book) {
        bookList[index] = item
        notifyItemChanged(index)
    }

    fun removeData(index: Int, item: Book) {
        bookList.remove(item)
        notifyItemRemoved(index)
    }

    fun clearData() {
        bookList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        Log.d(TAG, "onCreateViewHolder() viewType[$viewType]")
        return BookViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Log.d(TAG, "onBindViewHolder() position[$position]")
        val book = bookList[position]
        (holder as BookViewHolder).apply {
            bind(book)
            itemView.setOnClickListener { func.invoke(book) }
        }
    }

    override fun getItemCount(): Int = bookList.size

    inner class BookViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Book) {
//            Log.d(TAG, "bind() item[$item]")
            with(binding) {
                this.bookTitle.text = item.title
                this.bookIsbn.text = item.isbn
                this.bookPrice.text = item.price
                this.bookUrl.text = item.url
                this.bookImage.let {
                    it.scaleType = ImageView.ScaleType.CENTER_CROP
                    glideManager
                        .load(item.image)
                        .apply(RequestOptions().error(R.drawable.book))
                        .into(it)
                }
            }
        }
    }

    companion object {
        private const val TAG = "BookmarkAdapter"
    }
}