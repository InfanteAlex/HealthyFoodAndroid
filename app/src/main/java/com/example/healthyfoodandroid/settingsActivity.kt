package com.example.healthyfoodandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat

class settingsActivity : AppCompatActivity() {

    private var isDarkTheme = false

    private fun toggleTheme() {
        if (isDarkTheme) {
            setTheme(R.style.AppTheme_Light)
        } else {
            setTheme(R.style.AppTheme_Dark)
        }
        recreate() // Recreate the activity to apply the new theme
        isDarkTheme = !isDarkTheme // Toggle the theme flag
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val swtch = findViewById<Switch>(R.id.switchTheme) as Switch

        swtch.setOnCheckedChangeListener { _, isChecked ->
                    AppCompatDelegate.setDefaultNightMode(
                        if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                        else AppCompatDelegate.MODE_NIGHT_NO
                    )
                }
    }
    

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }




}