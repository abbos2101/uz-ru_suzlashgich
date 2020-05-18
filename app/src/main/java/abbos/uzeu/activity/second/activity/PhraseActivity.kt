package abbos.uzeu.activity.second.activity

import abbos.uzeu.R
import abbos.uzeu.activity.second.SecondApp
import android.os.Bundle

class PhraseActivity : SecondApp() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrase)

        init()
        getExtraDataSetSupportBar()
        activityPhrase()
    }
}
