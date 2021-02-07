package com.example.project3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mashape.unirest.http.Unirest
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection

private const val CONTACT_ACTIVITY_REQUEST_CODE = 0;

class MainActivity : AppCompatActivity() {

    private lateinit var viewButton :Button
    private lateinit var testText : TextView
    private lateinit var tempArray : JSONArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewButton = findViewById(R.id.view_button)
        testText = findViewById(R.id.test)

        viewButton.setOnClickListener { _->
            val size = tempArray.length()
            val namesList = mutableListOf<String>()
            val mailsList = mutableListOf<String>()
            val numbersList = mutableListOf<String>()
            var i = 0
            while(i<size){
                namesList.add(JSONObject(tempArray.getString(i)).getString("fullName"))
                mailsList.add(JSONObject(tempArray.getString(i)).getString("mail"))
                numbersList.add(JSONObject(tempArray.getString(i)).getString("number"))
                i++
            }
            startActivityForResult(ContactView.createIntent(this,namesList.toTypedArray(),
                mailsList.toTypedArray(), numbersList.toTypedArray()), CONTACT_ACTIVITY_REQUEST_CODE)
        }

        doAsync {
            val response = Unirest.get("https://contactsdb-0225.restdb.io/rest/contacts")
                .header("x-apikey", "5d915cec62b71c981ecaa0548e9bf18c3c7d8")
                .header("cache-control", "no-cache").asString()
            tempArray = JSONArray(response.body)
             //all = JSONObject(JSONArray(response.body).getString(0)).getString("age")
        }

//
        //val allRes = JSONObject(response.body).getString("age")

        //testText.text = allRes.toString()

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