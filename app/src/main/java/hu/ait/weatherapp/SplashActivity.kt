package hu.ait.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var logoAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_logo)
        logoAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                var intentScroll = Intent()

                intentScroll.setClass(this as SplashActivity, ScrollingActivity::class.java)

                startActivity(intentScroll)
            }
            override fun onAnimationStart(animation: Animation?) {
            }
        })

        imgvLogo.startAnimation(logoAnim)
    }
}
