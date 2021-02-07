package com.example.project3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactView : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_view)

        val contactsList = mutableListOf<Contact>(
            Contact("Mariana Lopez", 9991946753, "marlo@correo.com" ),
            Contact("Esteban Díaz", 9991973227, "ediaz@gmail.com"),
            Contact("Nina Mendez", 9992347567, "ninamendez@hotmail.com"),
            Contact("Maximiliano Torres", 9991005533, "maxto@correo.com"),
            Contact("Olivia Molina", 9991923465, "molivia@gmail.com")
        )



        viewManager = LinearLayoutManager(this)
        viewAdapter = ContactsAdapter(contactsList)

        recyclerView = findViewById<RecyclerView>(R.id.info_rv).apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@ContactView)
            adapter = viewAdapter
        }
    }
}