package com.example.project3

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object{
        const val EXTRA_NAME_INFO = "com.example.project3.fullName_info"
        const val EXTRA_MAIL_INFO = "com.example.project3.mail_info"
        const val EXTRA_PHONE_INFO = "com.example.project3.phone_info"

        fun createIntent(
            packageContext: Context, fullNameInfo: Array<String>, mailInfo: Array<String>, phoneInfo: Array<String>): Intent {
            return Intent(packageContext, ContactView::class.java).apply{
                putExtra(EXTRA_NAME_INFO, fullNameInfo)
                putExtra(EXTRA_MAIL_INFO, mailInfo)
                putExtra(EXTRA_PHONE_INFO, phoneInfo)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_view)

        val fullName = intent.getStringArrayExtra(EXTRA_NAME_INFO)
        val mail = intent.getStringArrayExtra(EXTRA_MAIL_INFO)
        val phone = intent.getStringArrayExtra(EXTRA_PHONE_INFO)
        var i = 0

        val contactsList = mutableListOf<Contact>(
            //Contact("Mariana Lopez", 9991946753, "marlo@correo.com" )
            //Contact("Esteban Díaz", 9991973227, "ediaz@gmail.com"),
            //Contact("Nina Mendez", 9992347567, "ninamendez@hotmail.com"),
            //Contact("Maximiliano Torres", 9991005533, "maxto@correo.com"),
            //Contact("Olivia Molina", 9991923465, "molivia@gmail.com")
        )

        phone?.forEach {
            contactsList.add(Contact(fullName!![i] , it.toLong(), mail!![i]) )
            i++
        }



        viewManager = LinearLayoutManager(this)
        viewAdapter = ContactsAdapter(contactsList)

        recyclerView = findViewById<RecyclerView>(R.id.info_rv).apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@ContactView)
            adapter = viewAdapter
        }
    }
}