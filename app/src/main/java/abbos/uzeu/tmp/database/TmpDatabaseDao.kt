package abbos.uzeu.tmp.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TmpDatabaseDao {
    @Insert
    fun insertGroup(it: GroupModel): Long

    @Insert
    fun insertUser(it: UserModel): Long
}