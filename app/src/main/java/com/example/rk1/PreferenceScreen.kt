package com.example.rk1

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class PreferenceScreen : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_preference_screen, rootKey)
        val sharedPreferences = preferenceScreen.sharedPreferences
        for (i in 0 until preferenceScreen.preferenceCount) {
            val p = preferenceScreen.getPreference(i)
            if (p !is CheckBoxPreference) {
                val value = sharedPreferences.getString(p.key, "")
                p.summary = value
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        preferenceScreen.findPreference<Preference>(resources.getString(R.string.pref_days_count))?.onPreferenceChangeListener = this
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        val preference = findPreference<Preference>(s)
        if (preference != null && preference !is CheckBoxPreference) {
            val value = sharedPreferences.getString(preference.key, "")
            preference.summary = value
        }
    }

    override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
        when (preference.key) {
            "daysCount" -> {
                val countOrNull = (value as? String)?.toIntOrNull()
                if (countOrNull == null || countOrNull !in 1 .. 365 * 10) {
                    Toast.makeText(context, resources.getString(R.string.days_range_error), Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        preference.summary = value as String
        return true
    }
}