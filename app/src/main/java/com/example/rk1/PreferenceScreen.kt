package com.example.rk1

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
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
                setPreferenceSummary(p, value)
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        val preference = findPreference<Preference>(s)
        if (preference != null && preference !is CheckBoxPreference) {
            val value = sharedPreferences.getString(preference.key, "")
            setPreferenceSummary(preference, value)
        }
    }

    private fun setPreferenceSummary(preference: Preference, value: String?) {
        if (preference is ListPreference) {
            val prefIndex = preference.findIndexOfValue(value)
            if (prefIndex >= 0) {
                preference.setSummary(preference.entries[prefIndex])
            }
        }
    }

    override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
        when (preference.key) {
            "daysCount" -> {
                val countOrNull = (value as? String)?.toIntOrNull() ?: return false
                if (countOrNull !in 1 .. 365 * 10) {
                    Toast.makeText(context, resources.getString(R.string.days_range_error), Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        preference.summary = value as String
        return true
    }
}