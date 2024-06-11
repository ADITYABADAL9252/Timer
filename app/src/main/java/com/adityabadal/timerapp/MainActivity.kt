package com.adityabadal.timerapp

import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import kotlin.text.*

class MainActivity : AppCompatActivity() {

    private lateinit var edthour:EditText
    private lateinit var edtmin:EditText
    private lateinit var edtsec:EditText
    private lateinit var timertext:TextView
    private lateinit var btnstart:Button
    private lateinit var btnreset:Button
    private lateinit var btnpause:Button
     private lateinit var timerset:Button

    private var timeLeftInMillis:Long=0
    private var timer:CountDownTimer?=null
    private var timerRunning=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         edthour=findViewById<EditText>(R.id.edthour)
         edtmin=findViewById<EditText>(R.id.edtminute)
         edtsec=findViewById<EditText>(R.id.edtsecond)
         timertext=findViewById<TextView>(R.id.timertext)
         btnstart=findViewById<Button>(R.id.btnstart)
         btnpause=findViewById<Button>(R.id.btnpause)
         btnreset=findViewById<Button>(R.id.btnreset)
         timerset=findViewById<Button>(R.id.timerset)

        timerset.setOnClickListener {
                   setTimer()
        }
        btnstart.setOnClickListener {
                 if (!timerRunning) {
                     startTimer()
                 }
        }
        btnreset.setOnClickListener {
                 resetTimer()
        }
        btnpause.setOnClickListener {
                if (timerRunning){
                    Pausetimer()
                }
        }
              updateTimerText()
              updateButton()


    }
    fun setTimer(){
        val hour=if (!TextUtils.isEmpty(edthour.text.toString())) edthour.text.toString().toLong() else 0
        val min=if(!TextUtils.isEmpty(edtmin.text.toString())) edtmin.text.toString().toLong() else 0
        val sec=if (!TextUtils.isEmpty(edtsec.text.toString())) edtsec.text.toString().toLong() else 0

        timeLeftInMillis = (hour * 3600 + min * 60 + sec)  * 1000
        if (timeLeftInMillis == 0L){
            Toast.makeText(this,"fill these following blanks to set timer",Toast.LENGTH_LONG).show()
            return
        }
        updateTimerText()
        updateButton()

    }

    private fun startTimer(){
        timer=object:CountDownTimer(timeLeftInMillis,1000){
            override  fun onTick(millisUntilFinished :Long){
                timeLeftInMillis=millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                 timerRunning = false
                updateButton()
            }
        }.start()
        timerRunning=true
        updateButton()
    }
    private fun Pausetimer(){
        timer?.cancel()
        timerRunning=false
        updateButton()
    }
    private fun resetTimer(){
        timer?.cancel()
        timeLeftInMillis=0
        timerRunning=false
        updateButton()
    }

    private fun updateTimerText(){
         val hour=(timeLeftInMillis / 1000)  / 3600
         val minute=((timeLeftInMillis / 1000) % 3600) / 60
         val second=(timeLeftInMillis / 1000) % 60
        timertext.text=String.format("%02d : %02d :%02d",hour,minute,second)
    }

    fun updateButton(){

        if (timerRunning){
          btnpause.isEnabled=true
          btnstart.isEnabled=false
          btnreset.isEnabled=false
          timerset.isEnabled=false
        }else{
            btnpause.isEnabled=false
            btnreset.isEnabled=true
            btnstart.isEnabled=true
            timerset.isEnabled=true
        }
    }

}