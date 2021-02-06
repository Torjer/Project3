package com.example.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var viewButton :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewButton = findViewById(R.id.view_button)

        viewButton.setOnClickListener { _->
            val intent = Intent(this, ContactView::class.java);
            startActivity(intent);
        }

        // Create URL

        // Create URL
       /* val restdbEndpoint = URL("https://inventory-fac4.restdb.io/rest/motorbikes")

// Create connection

// Create connection
        val myConnection: HttpsURLConnection = restdbEndpoint.openConnection() as HttpsURLConnection

        myConnection.setRequestProperty("User-Agent", "my-restdb-app")
        myConnection.setRequestProperty("Accept", "application/json")
        myConnection.setRequestProperty("x-apikey", "560bd47058e7ab1b2648f4e7")

        if (myConnection.getResponseCode() === 200) {
            // Success
            // Further processing here
        } else {
            // Error handling code goes here
        }*/
    }
}