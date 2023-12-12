package com.livewall.lawwalletfinalyearproject.UserActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.livewall.lawwalletfinalyearproject.Adapters.ChatboxAdapterCLass
import com.livewall.lawwalletfinalyearproject.ModelClass.ModelClassMessage
import com.livewall.lawwalletfinalyearproject.R
import com.livewall.lawwalletfinalyearproject.databinding.ActivityChatBoxBinding
import java.util.*
import kotlin.collections.ArrayList

class ChatBoxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBoxBinding
    private lateinit var senderID: String
    private lateinit var receiverID: String
    private lateinit var SenderRoom: String
    lateinit var ReciverRoom: String
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var list: ArrayList<ModelClassMessage>
    var storage: FirebaseStorage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = FirebaseStorage.getInstance()
        list = ArrayList();
        firebaseDatabase = FirebaseDatabase.getInstance()
        val receiverintentID = intent.getStringExtra("LawID")
        val receivername = intent.getStringExtra("name")
        val receiverImage = intent.getStringExtra("image")
        senderID = FirebaseAuth.getInstance().uid.toString()
        binding.namechatID.text = receivername.toString()
        receiverID = receiverintentID.toString()
        Toast.makeText(
            applicationContext,
            "userID" + senderID + "receiver" + receiverintentID,
            Toast.LENGTH_SHORT).show();
        SenderRoom = senderID + receiverintentID
        ReciverRoom = receiverintentID + senderID
        binding.sendimageID.setOnClickListener {
            if (binding.edmsgsendID.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "please Enter Text", Toast.LENGTH_SHORT).show()
            } else {
                val msg = ModelClassMessage(senderID, binding.edmsgsendID.text.toString())
                val randomkey = firebaseDatabase.reference.push().key
                firebaseDatabase.reference.child("Chats").child(SenderRoom).child("message")
                    .child(randomkey!!).setValue(msg).addOnSuccessListener {

                        firebaseDatabase.reference.child("Chats").child(ReciverRoom)
                            .child("message")
                            .child(randomkey!!).setValue(msg).addOnSuccessListener {
                                binding.edmsgsendID.text = null
                                Toast.makeText(
                                    applicationContext,
                                    "Message Sent",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                    }
            }
        }

        firebaseDatabase.reference.child("Chats").child(SenderRoom).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (snap1 in snapshot.children) {
                        val data = snap1.getValue(ModelClassMessage::class.java)
                        list.add(data!!)
                    }
                    binding.rvchatId.adapter = ChatboxAdapterCLass(this@ChatBoxActivity, list);
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatBoxActivity, "Error ", Toast.LENGTH_SHORT).show()
                }

            }
            )

        Clicklistener()
    }

    private fun Clicklistener() {
        binding.imageViewarrow.setOnClickListener {
            startActivity(Intent(applicationContext, ChatUserToLawyerFragment::class.java))
        }
//override fun onBackPressed() {
//    // Add your code here to handle the back button press
//    val fragmentManager: FragmentManager = supportFragmentManager
//    val currentFragment: Fragment? = fragmentManager.findFragmentById(R.id.framelayoutid)
//
//    // Replace the current fragment with the desired fragment
//    val desiredFragment = ChatUserToLawyerFragment()
//    fragmentManager.beginTransaction()
//        .replace(R.id.framelayoutid, desiredFragment)
//        .commit() // Optional: If you want to finish the current activity
//}

    }
}