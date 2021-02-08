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
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project3.ContactView.Companion.createIntent
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

        fun createIntent(
            packageContext: Context, fullNameInfo: Array<String>, mailInfo: Array<String>, phoneInfo: Array<String>): Intent {
            return Intent(packageContext, ContactView::class.java).apply{
                putExtra(EXTRA_NAME_INFO, fullNameInfo)
                putExtra(EXTRA_MAIL_INFO, mailInfo)
                putExtra(EXTRA_PHONE_INFO, phoneInfo)
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
        var i = 0

        val contactsList = mutableListOf<Contact>()

        phone?.forEach {
            contactsList.add(Contact(fullName!![i] , it.toLong(), mail!![i]) )
            i++
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
            with(dialog){
                setTitle("Add Contact")
                setPositiveButton("OK"){
                        dialog, which ->
                    contactsList.add(Contact(editName.text.toString(), editPhone.text.toString().toLong(), editEmail.text.toString()))
                    viewAdapter.notifyDataSetChanged()
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
                setPositiveButton("OK"){
                        dialog, which ->
                    contactsList.add(Contact(editName.text.toString(), editPhone.text.toString().toLong(), editEmail.text.toString()))
                    viewAdapter.notifyDataSetChanged()
                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.d("Main","Negative")
                }
                setView(dialogLayout)
                show()
            }

        }
        if(position <0){ DelBtn.isEnabled = false}
        else{DelBtn.isEnabled = true}
        DelBtn.setOnClickListener { _->
            val dialog = AlertDialog.Builder(this)
            with(dialog) {
                dialog.setTitle("Do you want to delete this contact?")
                setPositiveButton("Delete"){dialog, wich ->
                    contactsList.removeAt(position)
                    viewAdapter.notifyDataSetChanged()
                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.d("Main","Negative")
                }
                show()
            }
        }

        SaveBtn.setOnClickListener {
            val returningNamesList = mutableListOf<String>()
            val returningMailsList = mutableListOf<String>()
            val returningNumbersList = mutableListOf<String>()
            var j = 0
            while(j<contactsList.size){
                returningNamesList.add(contactsList[j].fullName)
                returningMailsList.add(contactsList[j].mail)
                returningNumbersList.add(contactsList[j].phoneNumber.toString())
                j++
            }

        }
    }

    fun getItem(Position : Int){
        position = Position
    }

    fun updateButtons(){
        DelBtn.isEnabled = true
        ModBtn.isEnabled = true
    }
}