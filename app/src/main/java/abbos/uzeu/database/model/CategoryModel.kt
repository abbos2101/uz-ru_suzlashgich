package abbos.uzeu.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,
    val cat_ru: String,
    val cat_uz: String,
    val cat_id: Int,
    val img: String?
)