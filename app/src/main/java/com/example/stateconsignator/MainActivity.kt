package com.example.stateconsignator
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var eventListener : ValueEventListener
    val disj1Ref = FirebaseDatabase.getInstance().getReference("Relais1")
    val disj2Ref = FirebaseDatabase.getInstance().getReference("Relais2")
    val disj3Ref = FirebaseDatabase.getInstance().getReference("Relais3")
    val disj4Ref = FirebaseDatabase.getInstance().getReference("Relais4")
    lateinit var state1 : ImageView
    lateinit var state2 : ImageView
    lateinit var state3 : ImageView
    lateinit var state4 : ImageView
    lateinit var btHist1 : MaterialButton
    lateinit var btHist2 : MaterialButton
    lateinit var btHist3 : MaterialButton
    lateinit var btHist4 : MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        state1 = findViewById(R.id.state_indicator_1)
        state2 = findViewById(R.id.state_indicator_2)
        state3 = findViewById(R.id.state_indicator_3)
        state4 = findViewById(R.id.state_indicator_4)
        btHist1 = findViewById(R.id.historique_1)
        btHist2 = findViewById(R.id.historique_2)
        btHist3 = findViewById(R.id.historique_3)
        btHist4 = findViewById(R.id.historique_4)
        btHist1.setOnClickListener {
            changeActivity("Relais1")
        }
        btHist2.setOnClickListener {
            changeActivity("Relais2")
        }
        btHist3.setOnClickListener {
            changeActivity("Relais3")
        }
        btHist4.setOnClickListener {
            changeActivity("Relais4")
        }
       eventListener =  disj1Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val stext = snapshot.child("/etat").value.toString().toInt()
                    if (stext == 1){
                        state1.setColorFilter(resources.getColor(R.color.green))
                    }else{
                        state1.setColorFilter(resources.getColor(R.color.red))
                    }

                    val d = snapshot.child("/date").value.toString();
                    val h = snapshot.child("/heure").value.toString();

                    findViewById<TextView>(R.id.tv_date_1).text = "$d \t$h"
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        //Disjoncteur 2
        disj2Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val stext = snapshot.child("/etat").value.toString().toInt()
                if (stext == 1){
                    state2.setColorFilter(resources.getColor(R.color.green))
                }else{
                    state2.setColorFilter(resources.getColor(R.color.red))
                }

                val d = snapshot.child("/date").value.toString();
                val h = snapshot.child("/heure").value.toString();

                findViewById<TextView>(R.id.tv_date_2).text = "$d \t$h"
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        //Disjoncteur 3
        disj3Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val stext = snapshot.child("/etat").value.toString().toInt()
                if (stext == 1){
                    state3.setColorFilter(resources.getColor(R.color.green))
                }else{
                    state3.setColorFilter(resources.getColor(R.color.red))
                }

                val d = snapshot.child("/date").value.toString();
                val h = snapshot.child("/heure").value.toString();

                findViewById<TextView>(R.id.tv_date_3).text = "$d \t$h"
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        //Disjoncteur 4
        disj4Ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               try {
                   val stext = snapshot.child("/etat").value.toString().toInt()
                   if (stext == 1){
                       state4.setColorFilter(resources.getColor(R.color.green))
                   }else{
                       state4.setColorFilter(resources.getColor(R.color.red))
                   }

                   val d = snapshot.child("/date").value.toString();
                   val h = snapshot.child("/heure").value.toString();

                   findViewById<TextView>(R.id.tv_date_4).text = "$d \t$h"
               }
               catch (_: Exception){}
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun changeActivity(rname: String) {
        val intent = Intent(this,Historique::class.java)
        disj1Ref.removeEventListener(eventListener)
        intent.putExtra("relayName",rname)
        startActivity(intent)
    }
}