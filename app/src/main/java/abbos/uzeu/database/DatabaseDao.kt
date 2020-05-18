package abbos.uzeu.database

import abbos.uzeu.database.model.CategoryModel
import abbos.uzeu.database.model.PhraseModel
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface DatabaseDao {
    @Query("select * from cats")
    fun loadCategories(): MutableList<CategoryModel>

    @Query("select * from phrases where cat_id=:catId")
    fun loadPhrasesBycatId(catId: Int): MutableList<PhraseModel>

    @Query("select * from phrases where is_fav = 'true'")
    fun loadPhrasesByfav(): MutableList<PhraseModel>

    @Query("select * from phrases where uz like :searchText or ru like :searchText")
    fun loadPhrasesBysearchText(searchText: String): MutableList<PhraseModel>

    @Query("update phrases set is_fav=:isFav where _id=:id")
    fun setFav(id: Int, isFav: String)

    @Query("select is_fav from phrases where _id=:id")
    fun getFav(id: Int): String
}