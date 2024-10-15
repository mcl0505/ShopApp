package com.mh.shop

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ShopConfig.startShop("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsb2NhbGhvc3QiLCJhdWQiOiJsb2NhbGhvc3QiLCJpYXQiOjE3Mjg2MDk4ODMsIm5iZiI6MTcyODYwOTg4MywiZXhwIjoxNzI5MjE0NjgzLCJ1c2VyIjp7Im1vYmlsZSI6IjE0Nzg1NTY0NDMyIiwibmlja25hbWUiOiJcdTVmMjBcdTRlMDkiLCJhdmF0YXIiOiIiLCJwTW9iaWxlIjoiMTQ3ODU1NjQ0MzEifSwianRpIjp7InVpZCI6MCwidHlwZSI6IiJ9fQ.uUOGesO6tOx0m6BfTIop9JpPJxLP80JaLtBGKBtC904")

    }
}