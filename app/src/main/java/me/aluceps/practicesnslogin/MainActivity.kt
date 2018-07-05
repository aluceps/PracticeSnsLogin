package me.aluceps.practicesnslogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.AccessToken
import com.twitter.sdk.android.core.SessionManager
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val twitterInfo: SessionManager<TwitterSession>? by lazy {
        TwitterCore.getInstance().sessionManager
    }

    private val facebookInfo: AccessToken? by lazy {
        AccessToken.getCurrentAccessToken()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isTwitterLoggedIn = twitterInfo == null
        val isFacebookLoggedIn = facebookInfo != null && !(facebookInfo?.isExpired ?: false)

        when (!isTwitterLoggedIn && !isFacebookLoggedIn) {
            true -> showLogin()
            else -> showToast()
        }
    }

    private fun showLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun showToast() {
        twitterInfo?.let {
            it.activeSession?.let {
                val message = "### twitter userId=${it.userId}"
                Timber.d(message)
                this.toast(message, Toast.LENGTH_SHORT)
            }
        }

        facebookInfo?.let {
            if (!it.isExpired) {
                val message = "### facebook userId=${it.userId}"
                Timber.d(message)
                this.toast(message, Toast.LENGTH_SHORT)
            }
        }
    }
}
