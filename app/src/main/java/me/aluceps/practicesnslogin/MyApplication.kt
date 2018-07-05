package me.aluceps.practicesnslogin

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTwitter()
        initializeFacebook()
        initializeTimber()
    }

    private fun initializeTwitter() {
        Twitter.initialize(TwitterConfig.Builder(applicationContext)
                .logger(DefaultLogger(Log.ERROR))
                .twitterAuthConfig(TwitterAuthConfig(getString(R.string.twitter_app_key), getString(R.string.twitter_app_secret)))
                .debug(true)
                .build())
    }

    private fun initializeFacebook() {
//        FacebookSdk.sdkInitialize(applicationContext)

    }

    private fun initializeTimber() {
        Timber.plant(Timber.DebugTree())
    }
}