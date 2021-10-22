package com.example.rk1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference

class SettingsActivity : AppCompatActivity(), Preference.OnPreferenceChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
        when (preference.key) {
            "daysCount" -> {
                val countOrNull = (value as? String)?.toIntOrNull() ?: return false
                if (countOrNull !in 1 .. 365 * 10) {
                    Toast.makeText(this, resources.getString(R.string.days_range_error), Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}