package abbos.uzeu.database

import abbos.uzeu.database.model.CategoryModel
import abbos.uzeu.database.model.PhraseModel
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryModel::class, PhraseModel::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}