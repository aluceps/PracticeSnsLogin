package me.aluceps.practicesnslogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private val facebookCallbackManager: CallbackManager by lazy {
        CallbackManager.Factory.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initializeTwitterCallback()
        initializeFacebookCallback()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        twitter_login.onActivityResult(requestCode, resultCode, data)
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun initializeTwitterCallback() {
        twitter_login.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                result?.data?.let {
                    Timber.d("### id=${it.id} userId=${it.userId}")
                }
                this@LoginActivity.toast("success", Toast.LENGTH_SHORT)
            }

            override fun failure(exception: TwitterException?) {
                Timber.d("### exception=$exception")
                this@LoginActivity.toast("failure", Toast.LENGTH_SHORT)
            }
        }
    }

    private fun initializeFacebookCallback() {
        facebook_login.registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                result?.accessToken?.let {
                    Timber.d("### applicationId=${it.applicationId} userId=${it.userId}")
                }
                this@LoginActivity.toast("onSuccess", Toast.LENGTH_SHORT)
            }

            override fun onCancel() {
                this@LoginActivity.toast("onCancel", Toast.LENGTH_SHORT)
            }

            override fun onError(error: FacebookException?) {
                Timber.d("### error=$error")
                this@LoginActivity.toast("onError", Toast.LENGTH_SHORT)
            }
        })
    }
}