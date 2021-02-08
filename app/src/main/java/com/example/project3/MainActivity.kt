package com.example.project3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mashape.unirest.http.Unirest
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject

private const val CONTACT_ACTIVITY_REQUEST_CODE = 0;

class MainActivity : AppCompatActivity() {

    private lateinit var viewButton :Button
    private lateinit var testText : TextView
    private lateinit var tempArray : JSONArray


    companion object{
        const val EXTRA_RETURNING_NAME_INFO = "com.example.project3.returningFullName_info"
        const val EXTRA_RETURNING_MAIL_INFO = "com.example.project3.returningMail_info"
        const val EXTRA_RETURNING_PHONE_INFO = "com.example.project3.returningPhone_info"

        fun createIntent(
            packageContext: Context, returningFullNameInfo: Array<String>, returningMailInfo: Array<String>, returningPhoneInfo: Array<String>): Intent {
            return Intent(packageContext, MainActivity::class.java).apply{
                putExtra(EXTRA_RETURNING_NAME_INFO, returningFullNameInfo)
                putExtra(EXTRA_RETURNING_MAIL_INFO, returningMailInfo)
                putExtra(EXTRA_RETURNING_PHONE_INFO, returningPhoneInfo)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewButton = findViewById(R.id.view_button)
        testText = findViewById(R.id.test)

        val returningFullName = intent.getStringArrayExtra(EXTRA_RETURNING_NAME_INFO)
        val returningMail = intent.getStringArrayExtra(EXTRA_RETURNING_MAIL_INFO)
        val returningPhone = intent.getStringArrayExtra(EXTRA_RETURNING_PHONE_INFO)

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

//        doAsync {
//            val response = Unirest.post("https://contactsdb-0225.restdb.io/rest/contacts")
//                .header("content-type", "application/json")
//                .header("x-apikey", "5d915cec62b71c981ecaa0548e9bf18c3c7d8")
//                .header("cache-control", "no-cache")
//                .body("{\"fullName\":\"xyz\",\"mail\":\"abc@abc.com\",\"number\":\"12345678\"}")
//                .asString()
//        }

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