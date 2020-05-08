package ger.girod.interview.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ger.girod.interview.R
import ger.girod.interview.domain.model.UserModel
import ger.girod.interview.domain.model.UserModelView
import ger.girod.interview.ui.main.MainFragment

const val USER_EXTRA = "user"
class UserDetailActivity : AppCompatActivity() {

    companion object {

        fun getIntent(context: Context, userModel: UserModelView) : Intent {
            return Intent(context, UserDetailActivity::class.java).apply {
                putExtra(USER_EXTRA,  userModel)

            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance(intent.getParcelableExtra(USER_EXTRA)))
                .commitNow()
        }
    }
}