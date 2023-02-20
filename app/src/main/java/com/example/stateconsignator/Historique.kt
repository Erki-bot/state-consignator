package com.example.stateconsignator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stateconsignator.adapter.HistAdapter
import com.example.stateconsignator.data.Hist
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Historique : AppCompatActivity() {
    lateinit var firebaseRef :DatabaseReference
    lateinit var recyclerView: RecyclerView
    lateinit var eventListener: ValueEventListener
    lateinit var histAdapter: HistAdapter
    lateinit var titre : TextView
    var histList = ArrayList<Hist>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)
        titre = findViewById(R.id.historique_title)
        recyclerView = findViewById(R.id.hist_recycler_view)
        val relayName =  intent.getStringExtra("relayName")
        Log.i("NomRelais","$relayName")
        titre.text = "Historique $relayName"
        firebaseRef = FirebaseDatabase.getInstance().getReference("$relayName/historique")
        refreshList()
        eventListener = firebaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    histList.clear()
                    for (child in snapshot.children){
                        val hist = child.getValue(Hist::class.java)
                        if (hist != null){
                            histList.add(hist)
                        }
                    }
                    refreshList()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        firebaseRef.removeEventListener(eventListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseRef.removeEventListener(eventListener)
    }

    private fun refreshList() {
        histAdapter = HistAdapter(this,histList,resources)
        recyclerView.apply {
            adapter = histAdapter
            layoutManager = LinearLayoutManager(
                this@Historique, RecyclerView.VERTICAL, false
            )
            //setHasFixedSize(true)
        }
    }
}