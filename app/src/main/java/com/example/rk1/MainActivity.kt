package com.example.rk1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var logT = "DISPLAY LOG: "

    override fun onPause() {
        super.onPause()
        logT += "onPause "
        Log.w("events", "omPause")
    }

    override fun onStop() {
        super.onStop()
        logT += "onStop "
        Log.w("events", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        logT += "onDestroy "
        Log.w("events", "onDestroy")
    }

    override fun onStart() {
        super.onStart()
        logT += "onStart "
        Log.w("events", "onStart")
    }

    override fun onResume() {
        super.onResume()
        logT += "onResume "
        Log.w("events", "onResume")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logT += "onCreate "
        Log.w("events", "onCreate")
        setContentView(R.layout.activity_main)

        val textLog = findViewById<TextView>(R.id.textLog)
        textLog.text = logT

        viewManager = LinearLayoutManager(this)
        viewAdapter = ListAdapter()
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        NetworkService.getJSONApi()
            ?.getData("bash", 50)
            ?.enqueue(object : Callback<List<PostModel?>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<PostModel?>?>,
                response: Response<List<PostModel?>?>
            ) {
                response.body()?.forEach { it ->
                    (viewAdapter as ListAdapter).addData(it)
                }
                recyclerView.adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<PostModel?>?>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "An error occurred during networking $t",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
