package com.example.rk1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_main.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_main : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var currencyTag: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val linkText = view.findViewById<TextView>(R.id.link_web)
        val currencyText = view.findViewById<EditText>(R.id.currency_text)
        val button = view.findViewById<Button>(R.id.search_button)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_view)
        currencyTag = currencyText.text.toString().uppercase(Locale.getDefault())
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        fun getDaysCount(): Int =
            (prefs.getString(resources.getString(R.string.pref_days_count), "1") ?: "1").toInt()
        fun getCurrency(): String =
            prefs.getString(resources.getString(R.string.pref_currency), "USD") ?: "USD"
        updateListAdapter(view, currencyTag, getCurrency(), getDaysCount())

        button.setOnClickListener {
            currencyTag = currencyText.text.toString().uppercase(Locale.getDefault())
            updateListAdapter(view, currencyTag, getCurrency(), getDaysCount())
        }

        linkText.setOnClickListener{
            currencyTag = currencyText.text.toString().uppercase(Locale.getDefault())
            val url = "https://www.cryptocompare.com/coins/${currencyTag.lowercase(Locale.getDefault())}/overview/${getCurrency()}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        return view
    }

    fun updateListAdapter(view: View, currencyFrom: String, currenctTo: String, limit: Int) {
        viewManager = LinearLayoutManager(view.context)
        viewAdapter = ListAdapter(currenctTo)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        NetworkService.getJSONApi()
            ?.getData(currencyFrom, currenctTo, limit - 1)
            ?.enqueue(object : Callback<ApiResponse?> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>
                ) {
                    val body = response.body()
                    if (body?.data != null) {
                        if (body.data?.rows == null) {
                            Toast.makeText(view.context, R.string.invalid_code_error,
                                Toast.LENGTH_LONG).show()
                        }
                        body.data?.rows?.forEach { it ->
                            (viewAdapter as ListAdapter).addData(it, view)
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()
                    } else {
                        Toast.makeText(view.context, R.string.runtime_api_error,
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                    Toast.makeText(
                        view.context,
                        "Error: $t",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_main.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_main().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}