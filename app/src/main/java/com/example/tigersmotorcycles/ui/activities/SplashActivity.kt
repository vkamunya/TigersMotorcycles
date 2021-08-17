package com.example.tigersmotorcycles.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tigersmotorcycles.R
import com.example.tigersmotorcycles.firestore.FirestoreClass


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {

            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

        }
        @Suppress("DEPRECATION")
        Handler().postDelayed(
                {
                    val currentUserID = FirestoreClass().getCurrentUserID()

                    if (currentUserID.isNotEmpty()) {
                        // Launch dashboard screen.
                        startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        finish()
                    }
                },
        2500

        )

       // val appName:TextView = findViewById(R.id.the_app_name)
       // appName.text = "Captain Motorcycles"
       // val typeface: Typeface = Typeface.createFromAsset(assets, "Montserrat-Bold.ttf")
       // appName.typeface= typeface









        

    }
}