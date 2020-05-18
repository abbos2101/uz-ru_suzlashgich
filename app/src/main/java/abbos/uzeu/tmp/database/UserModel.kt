package abbos.uzeu.tmp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,
    val groupId: Long,
    val name: String,
    val surname: String,
    val age: Int,
    val adsress: String?,
    val phoneNumber: String?
)