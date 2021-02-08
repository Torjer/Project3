package com.example.project3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_item.view.*
import org.w3c.dom.Text

class ContactsAdapter (private val context: Context, private val contactsList: List<Contact>): RecyclerView.Adapter<ContactsAdapter.ContactsHolder>(){


    class ContactsHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ContactsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ContactsHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsHolder, position: Int){
        //val contact = contactsList[position]
        holder.view.findViewById<TextView>(R.id.contactname_text).text =contactsList[position].fullName
        holder.view.findViewById<TextView>(R.id.contactphone_text).text =contactsList[position].phoneNumber.toString()
        holder.view.findViewById<TextView>(R.id.contactemail_text).text =contactsList[position].mail
        holder.view.setOnClickListener{//prueba
            (context as ContactView).getItem(holder.adapterPosition)
            (context as ContactView).updateButtons()
            Toast.makeText( (context as ContactView),holder.adapterPosition.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount() = contactsList.size
}