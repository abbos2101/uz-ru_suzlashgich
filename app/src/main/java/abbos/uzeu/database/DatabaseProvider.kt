package abbos.uzeu.database

import abbos.uzeu.common.baseName
import android.content.Context
import androidx.room.Room

class DatabaseProvider {
    companion object {
        private var instance: MyDatabase? = null
        fun instance(ctx: Context): MyDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(ctx, MyDatabase::class.java, "$baseName")
                    .createFromAsset("$baseName")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}