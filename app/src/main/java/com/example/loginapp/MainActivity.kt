package com.example.loginapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : Activity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = Firebase.auth




        val logout = findViewById<Button>(R.id.logout)

        logout.setOnClickListener {

            auth.signOut()

            Toast.makeText(baseContext, "logged out",
                Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }







        val user = auth.currentUser
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name1 = user.displayName
                        val name = user.displayName
                        val phone = document.get("phone") as String

                        val userInfoTextView = findViewById<TextView>(R.id.name21)
                        userInfoTextView.text = "Welcome $name\n \n Your Phone Number Is: $phone"



                    }
                }
                .addOnFailureListener { exception ->
                    // Failed to retrieve user data

                    Toast.makeText(baseContext, "Failed to retrieve user data",
                        Toast.LENGTH_SHORT).show()
                }
        }





    }
}