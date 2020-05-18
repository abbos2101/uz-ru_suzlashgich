package abbos.uzeu.tmp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupModel(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,
    val groupName: String
)