package com.positivo.kotlinfbtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.positivo.kotlinfbtest.model.Tester
import com.positivo.kotlinfbtest.model.TesterAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tester_adapter.*

class MainActivity : AppCompatActivity() {
    lateinit var listTester: ArrayList<Tester>
    lateinit var testerAdapter: TesterAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        database = FirebaseDatabase.getInstance().getReference("testers")

        getInformation()

        btnCreate.setOnClickListener {
            val key = database.push().key as String
            val tester = Tester("Testerzao", "akakaka")
            sendToFirebase(key, tester)
        }

    }

    fun getInformation() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                listTester = ArrayList()
                for (postSnapshot in dataSnapshot.children) {
                    val post = postSnapshot.getValue(Tester::class.java) as Tester
                    Log.d("TESTE", "name " + post.name)

                    val tester = Tester(post.name, post.codigo)
                    listTester.add(tester)
                }

                testerAdapter = TesterAdapter(listTester)
                recyclerView = listRecyclerView
                recyclerView.apply {
                    adapter = testerAdapter
                    layoutManager = LinearLayoutManager(this@MainActivity)
                }
                testerAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TESTE", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }

        database.addValueEventListener(postListener)
    }

    fun sendToFirebase(key: String, tester: Tester): Boolean {
        val myRef = database
        myRef.child(key).setValue(tester)
            .addOnSuccessListener {
                Log.d("TESTE", "ENVIOU PRO FIREBASE")
                val view = findViewById<View>(R.id.mainActivityId)
                //Snackbar.make(view, "Tester criado com sucesso.", Snackbar.LENGTH_LONG).show()
                Toast.makeText(this, "Tester criado com sucesso.", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Log.d("TESTE", "ERRO NO FIREBASE")
            }

        return true
    }

    fun itemClick(view: View) {
        val name = view.findViewById<TextView>(R.id.name)
        Log.d("TESTE", "clicou " + name.text)
    }
}
