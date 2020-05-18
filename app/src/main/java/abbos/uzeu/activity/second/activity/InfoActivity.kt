package abbos.uzeu.activity.second.activity

import abbos.uzeu.R
import abbos.uzeu.activity.second.SecondApp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class InfoActivity : SecondApp() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        getExtraDataSetSupportBar()
    }
}
