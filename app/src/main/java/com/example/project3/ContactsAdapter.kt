package com.example.project3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactsAdapter (private val contactsList: List<Contact>): RecyclerView.Adapter<ContactsAdapter.ContactsHolder>(){

    class ContactsHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ContactsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
        return ContactsHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsHolder, position: Int){
        holder.view.findViewById<TextView>(R.id.contactname_text).text =contactsList[position].fullName
        holder.view.findViewById<TextView>(R.id.contactphone_text).text =contactsList[position].phoneNumber.toString()
        holder.view.findViewById<TextView>(R.id.contactemail_text).text =contactsList[position].mail
    }

    override fun getItemCount() = contactsList.size
}