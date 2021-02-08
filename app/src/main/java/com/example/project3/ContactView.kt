package com.example.project3

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.ContactView.Companion.createIntent
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONObject
import java.nio.file.Files.size

private const val CONTACT_ACTIVITY_REQUEST_CODE = 1

class ContactView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object{
        const val EXTRA_NAME_INFO = "com.example.project3.fullName_info"
        const val EXTRA_MAIL_INFO = "com.example.project3.mail_info"
        const val EXTRA_PHONE_INFO = "com.example.project3.phone_info"
        const val EXTRA_ID_INFO = "com.example.project3.id_info"

        fun createIntent(
            packageContext: Context, idInfo:Array<String>, fullNameInfo: Array<String>, mailInfo: Array<String>, phoneInfo: Array<String>): Intent {
            return Intent(packageContext, ContactView::class.java).apply{
                putExtra(EXTRA_NAME_INFO, fullNameInfo)
                putExtra(EXTRA_MAIL_INFO, mailInfo)
                putExtra(EXTRA_PHONE_INFO, phoneInfo)
                putExtra(EXTRA_ID_INFO, idInfo)
            }
        }
    }

    private lateinit var AddBtn: Button
    private lateinit var SaveBtn: Button
    private lateinit var ModBtn: Button
    private lateinit var DelBtn: Button
    private var position = -1

    private var added = mutableListOf<String>()
    private var modfif = mutableListOf<String>()
    private var deleted = mutableListOf<String>()
    private var SelectedItem:String = " "
    private var SelectedContact:String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_view)

        AddBtn = findViewById(R.id.add_button)
        DelBtn = findViewById(R.id.delete_button)
        SaveBtn = findViewById(R.id.save_button)
        ModBtn = findViewById(R.id.modify_button)

        val fullName = intent.getStringArrayExtra(EXTRA_NAME_INFO)
        val mail = intent.getStringArrayExtra(EXTRA_MAIL_INFO)
        val phone = intent.getStringArrayExtra(EXTRA_PHONE_INFO)
        val idList = intent.getStringArrayExtra(EXTRA_ID_INFO)
        var tempId = mutableListOf<String>()
        var i = 0

        val contactsList = mutableListOf<Contact>()

        //getResponse()

        phone?.forEach {
            contactsList.add(Contact( idList!![i],fullName!![i] , it.toLong(), mail!![i]) )
            tempId.add(idList[i])
            i++
        }

        if(position <0){
            DelBtn.isEnabled = false
            ModBtn.isEnabled = false
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = ContactsAdapter(this,contactsList)

        recyclerView = findViewById<RecyclerView>(R.id.info_rv).apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@ContactView)
            adapter = viewAdapter
        }
        AddBtn.setOnClickListener { _->
            val dialog = AlertDialog.Builder(this)
            val dialogLayout = layoutInflater.inflate(R.layout.add_contact_layout, null)
            val editName = dialogLayout.findViewById<EditText>(R.id.new_contact)
            val editPhone =  dialogLayout.findViewById<EditText>(R.id.new_phone)
            val editEmail =  dialogLayout.findViewById<EditText>(R.id.new_email)
            //val id = JSONObject(JSONArray(response.body).getString(position)).getString("_id")
            with(dialog){
                setTitle("Add Contact")
                setPositiveButton("OK"){
                        dialog, which ->
                    var tempid = ""
                    doAsync {
                        val response = Unirest.post("https://contactsdb-0225.restdb.io/rest/contacts")
                            .header("content-type", "application/json")
                            .header("x-apikey", "5d915cec62b71c981ecaa0548e9bf18c3c7d8")
                            .header("cache-control", "no-cache")
                            .body("{\"fullName\":\"${editName.text.toString()}\", \"mail\":\"${editEmail.text.toString()}\", \"number\":\"${editPhone.text.toString()}\"}")
                            .asString();
                        tempId.add(JSONObject(response.body).getString("_id"))
                    }
                    contactsList.add(Contact( tempid, editName.text.toString(), editPhone.text.toString().toLong(), editEmail.text.toString()))
                    viewAdapter.notifyDataSetChanged()
                    //getResponse()
                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.d("Main","Negative")
                }
                setView(dialogLayout)
                show()
            }

        }

        ModBtn.setOnClickListener { _->
            val dialog = AlertDialog.Builder(this)
            val dialogLayout = layoutInflater.inflate(R.layout.add_contact_layout, null)
            val editName = dialogLayout.findViewById<EditText>(R.id.new_contact)
            val editPhone =  dialogLayout.findViewById<EditText>(R.id.new_phone)
            val editEmail =  dialogLayout.findViewById<EditText>(R.id.new_email)
            with(dialog){
                setTitle("Modify Contact?")
                setPositiveButton("OK"){dialog, which ->
                    val id = tempId[position]
                    contactsList.forEach {
                        if(it.fullName==contactsList[position].fullName){
                            //contactsList[position]=Contact( editName.text.toString(), editPhone.text.toString().toLong(), editEmail.text.toString())
                            doAsync {
                               val response = Unirest.put("https://contactsdb-0225.restdb.io/rest/contacts/$id")
                                    .header("content-type", "application/json")
                                    .header("x-apikey", "5d915cec62b71c981ecaa0548e9bf18c3c7d8")
                                    .header("cache-control", "no-cache")
                                    .body("{\"fullName\":\"${editName.text.toString()}\", \"mail\":\"${editEmail.text.toString()}\", \"number\":\"${editPhone.text.toString()}\"}")
                                    .asString();
                            }
                        }
                    }
                    contactsList[position]= Contact( id, editName.text.toString(), editPhone.text.toString().toLong(), editEmail.text.toString())
                    viewAdapter.notifyDataSetChanged()
                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.d("Main","Negative")
                }
                setView(dialogLayout)
                show()
            }

        }
        DelBtn.setOnClickListener { _->
            val dialog = AlertDialog.Builder(this)
            with(dialog) {
                dialog.setTitle("Do you want to delete this contact?")
                val id = tempId[position]
                tempId.removeAt(position)
                setPositiveButton("Delete"){dialog, wich ->
                    contactsList.removeAt(position)
                    doAsync {
                        val response = Unirest.delete("https://contactsdb-0225.restdb.io/rest/contacts/$id")
                            .header("content-type", "application/json")
                            .header("x-apikey", "5d915cec62b71c981ecaa0548e9bf18c3c7d8")
                            .header("cache-control", "no-cache")
                            .asString()
                    }
                    viewAdapter.notifyDataSetChanged()
                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.d("Main","Negative")
                }
                show()
            }
        }

        SaveBtn.setOnClickListener {
            finish()
        }
    }

    fun getItem(Position : Int){
        position = Position
    }

    fun updateButtons(){
        DelBtn.isEnabled = true
        ModBtn.isEnabled = true
    }

    //fun getResponse(){
    //    doAsync {
    //        response = Unirest.get("https://contactsdb-0225.restdb.io/rest/contacts")
    //            .header("x-apikey", "5d915cec62b71c981ecaa0548e9bf18c3c7d8")
    //            .header("cache-control", "no-cache").asString()
//
    //    }
    //}

}