package com.example.task_onesharedpreference

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flavors = arrayOf("vanilla", "chocolate","strawberry")

        var username: String? = ""
        var age: Int = 0
        var wage: Float = 9.99f
        var prog: Int = 50
        var flavor_index: Int=0

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etAge= findViewById<EditText>(R.id.etAge)
        val etUserWage = findViewById<EditText>(R.id.etUserWage)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val sbProgress = findViewById<SeekBar>(R.id.sbProgress)
        val tvProgress = findViewById<TextView>(R.id.tvProgress)
        val spFlavor = findViewById<Spinner>(R.id.spFlavor)

        // establish a shared preference and share preference editor
        val shared_pref = getSharedPreferences("SP_DEMO", Context.MODE_PRIVATE)
        val shared_pref_editor = shared_pref.edit()

        //*************
        username = shared_pref.getString("USERNAME","Jafar")
        age = shared_pref.getInt("AGE",1)
        wage = shared_pref.getFloat("WAGE",7.25f)
        prog = shared_pref.getInt("PROG", 49)
        flavor_index = shared_pref.getInt("FLAV_INDEX", 0)

        //*************
        etUsername.setText(username)
        etAge.setText(""+age)
        etUserWage.setText(String.format("%.2f",wage))
        sbProgress.progress = prog
        tvProgress.setText(""+prog)

        //*********************************************************************************
        // method to save shared preference values to be used the next time
        btnSave.setOnClickListener{
            //retrieve data to be saved
            username = etUsername.text.toString()
            age = Integer.parseInt(etAge.text.toString())
            wage = etUserWage.text.toString().toFloat()
            prog = sbProgress.progress
            flavor_index = spFlavor.selectedItemId.toInt()

            //*************
            shared_pref_editor.putString("USERNAME",username)
            shared_pref_editor.putInt("AGE",age)
            shared_pref_editor.putFloat("WAGE",wage)
            shared_pref_editor.putInt("PROG",prog)
            shared_pref_editor.putInt("FLAV_INDEX",flavor_index)

            shared_pref_editor.apply()  //IMPORTANT!!!!
            // like a "commit" in a database transaction -- nothing happens without the apply method
        }

        // add seek bar event listener and handler -- requires three methods
        sbProgress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            //during the user's "sliding"
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                tvProgress.text = "" + i
            }

            // in case you need to remember where you started
            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            // in case you only want to act when the user is done "sliding"
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }) //end setOnSeekBarChangeListener

        //************************************************************************************************************
        // populate the "Flavor" spinner from a coded array
        val aaFlavor = ArrayAdapter(this, android.R.layout.simple_spinner_item, flavors)
        aaFlavor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view
        spFlavor.adapter = aaFlavor

        spFlavor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val tvUserItem = view as TextView
                Toast.makeText(applicationContext, "You chose " + tvUserItem.text.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
        spFlavor.setSelection(flavor_index)
    }
}