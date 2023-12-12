package com.livewall.lawwalletfinalyearproject.LaywerActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.livewall.lawwalletfinalyearproject.Adapters.ChatboxAdapterCLass
import com.livewall.lawwalletfinalyearproject.Adapters.LawyerChatboxAdapterCLass
import com.livewall.lawwalletfinalyearproject.LaywerActivity.Fragment.ViewusertochatFragment
import com.livewall.lawwalletfinalyearproject.ModelClass.ModelClassMessage
import com.livewall.lawwalletfinalyearproject.R
import com.livewall.lawwalletfinalyearproject.UserActivity.ChatUserToLawyerFragment
import com.livewall.lawwalletfinalyearproject.databinding.ActivityChatBoxBinding
import com.livewall.lawwalletfinalyearproject.databinding.ActivityChatwithuserLawyerBinding

class ChatwithuserActivityLawyer : AppCompatActivity() {
    private lateinit var binding: ActivityChatwithuserLawyerBinding
    private lateinit var senderID: String
    private lateinit var receiverID: String
    private lateinit var SenderRoom: String
    lateinit var ReciverRoom: String
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var list: ArrayList<ModelClassMessage>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatwithuserLawyerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = ArrayList();
        firebaseDatabase = FirebaseDatabase.getInstance()
        val receiverintentID = intent.getStringExtra("uid")
        val receivername = intent.getStringExtra("nameID")
//        val receiverImage = intent.getStringExtra("image")
        senderID = FirebaseAuth.getInstance().uid.toString()
        Toast.makeText(
            applicationContext,
            "userID" + senderID + "receiver" + receiverintentID,
            Toast.LENGTH_SHORT
        ).show();
        binding.namechatID.text = receivername.toString()
        receiverID = receiverintentID.toString()
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
                    binding.rvchatlawyerId.adapter =
                        LawyerChatboxAdapterCLass(this@ChatwithuserActivityLawyer, list);
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatwithuserActivityLawyer, "Error ", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            )
       Clicklistener()
    }

    private fun Clicklistener() {
        binding.imageViewarrow.setOnClickListener {
            startActivity(Intent(applicationContext,ViewusertochatFragment::class.java))
        }
    }
}