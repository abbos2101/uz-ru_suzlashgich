package abbos.uzeu.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrases")
data class PhraseModel(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val ru: String,
    val uz: String,
    val cat_id: Int,
    var is_fav: String?
)