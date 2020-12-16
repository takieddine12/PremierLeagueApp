package taki.eddine.premier.league.pro.appui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.coroutines.*
import taki.eddine.premier.league.pro.bottomsheetfragments.RatingBottomSheetDialog
import taki.eddine.premier.league.pro.Constants
import taki.eddine.premier.league.pro.R
import taki.eddine.premier.league.pro.databinding.ActivitySettingsBinding

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ActivitySettings : AppCompatActivity() {
    private lateinit var binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarSettings)
        supportFragmentManager.beginTransaction().replace(binding.framelayout.id,
        SettingsFragment()).commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                startActivity(Intent(this,MainActivity::class.java))
            }
        }
        return true
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class SettingsFragment : PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences,rootKey)
        setUpAllCheckBoxes()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constants.NOTIFICATION_TEAM_PICK){
            data?.let {

                val isChecked = data.getBooleanExtra("isChecked",false)
                val teamId = data.getIntExtra("teamId",0)
                val team = data.getStringExtra("team")
                val sharedPreferences = requireContext().getSharedPreferences("pickTeam",Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply {
                   putBoolean("isTeamChecked",isChecked)
                   putInt("teamId",teamId)
                    putString("team",team)
                   apply()
                }
            }
        }
    }
    private fun setUpAllCheckBoxes(){

        val notificationCheckBox = findPreference<CheckBoxPreference>("notification")

        //--
        val fcmNotificationPrefs = requireContext().getSharedPreferences("fcmPrefs", Context.MODE_PRIVATE)
        val editor  = fcmNotificationPrefs.edit()
        //--
        val teamPickerActivityPrefs = requireContext().getSharedPreferences("pickTeam", Context.MODE_PRIVATE)
        val team = teamPickerActivityPrefs.getString("team","")
        val teamId = teamPickerActivityPrefs.getInt("teamId",0)
        val isTeamChecked = teamPickerActivityPrefs.getBoolean("isTeamChecked",false)

        if(isTeamChecked){
            notificationCheckBox!!.summary = team
            notificationCheckBox.isChecked = true
            editor.apply {
                putBoolean("isTeamChecked",isTeamChecked)
                putInt("teamId",teamId)
                apply()
            }
        }


        notificationCheckBox!!.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                val isChecked = newValue as Boolean

                val sharedPreferences = requireContext().getSharedPreferences("pickTeam", Context.MODE_PRIVATE)
                val editorPickerPrefs = sharedPreferences.edit()

                if(isChecked){
                    notificationCheckBox.isChecked = true
                    val intent = Intent(requireContext(),ChangePickActivity::class.java)
                    startActivityForResult(intent, Constants.NOTIFICATION_TEAM_PICK)
                } else {
                    notificationCheckBox.isChecked = false
                    //----
                    editorPickerPrefs.apply {
                        putBoolean("isTeamChecked",false)
                        putInt("teamId",0)
                        putString("team","")
                    }
                    //---
                    notificationCheckBox.summary = ""
                    editor.putBoolean("isTeamChecked",isChecked)
                }
                editorPickerPrefs.apply()
                editor.apply()
                isChecked
            }

        //---------------------------------------------------------------------------------------------------------------------
        val vibrationCheckBox = findPreference<CheckBoxPreference>("vibration")
        vibrationCheckBox?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            val vibratePreferences = requireContext().getSharedPreferences("vibrationPrefs",Context.MODE_PRIVATE)
            val vibrationEditor = vibratePreferences.edit()

            val isChecked = newValue as Boolean
            if(isChecked){
                vibrationCheckBox?.isChecked = true
                vibrationEditor.putBoolean("vibrate",isChecked)
            } else {
                vibrationCheckBox?.isChecked = false
                vibrationEditor.putBoolean("vibrate",isChecked)
            }
            vibrationEditor.apply()
            isChecked
        }


        ///---------Changing Language
        ratingPreference()
        languagesPreferences()


    }
    private fun languagesPreferences(){
        val languagesSharedPreference  = requireContext().getSharedPreferences("languagesPrefs",
            Context.MODE_PRIVATE)
        val editor = languagesSharedPreference.edit()
        val englishCheckBox = findPreference<CheckBoxPreference>("english")
        val spanishCheckBox = findPreference<CheckBoxPreference>("spanish")
        val frenchCheckBox = findPreference<CheckBoxPreference>("french")
        englishCheckBox?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                val isChecked = newValue as Boolean
                if(isChecked){
                    spanishCheckBox?.isChecked = false
                    frenchCheckBox?.isChecked = false
                    editor.apply {
                        putString("language","en")
                        putString("code","en-EN")
                        apply()
                    }
                    Intent(requireContext(),MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
                isChecked
            }
        spanishCheckBox?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                val isChecked = newValue as Boolean
                if(isChecked){
                    englishCheckBox?.isChecked = false
                    frenchCheckBox?.isChecked = false

                    editor.apply {
                       putString("language","es")
                       putString("code","es-ES")
                        apply()
                    }

                    Intent(requireContext(),MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
                isChecked
            }
        frenchCheckBox?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, newValue ->
                val isChecked = newValue as Boolean
                if(isChecked){
                    englishCheckBox?.isChecked = false
                    spanishCheckBox?.isChecked = false

                    editor.apply {
                        putString("language","fr")
                        putString("code","fr-FR")
                        apply()
                    }

                    Intent(requireContext(),MainActivity::class.java).also {
                        startActivity(it)
                    }
                }
                isChecked
            }
    }
    private fun ratingPreference(){
        //--- Rating Bar Preference  -----//
        val preferenceRatingDialog = findPreference<Preference>("rate")
        preferenceRatingDialog!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            RatingBottomSheetDialog().apply {
                show(requireActivity().supportFragmentManager,"tag")
            }

            true
        }
    }
}