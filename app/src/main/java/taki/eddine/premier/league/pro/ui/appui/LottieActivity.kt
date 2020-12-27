package taki.eddine.premier.league.pro.ui.appui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import maes.tech.intentanim.CustomIntent.customType
import taki.eddine.premier.league.pro.databinding.ActivityLottieBinding

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class LottieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLottieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLottieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val intent = Intent(this@LottieActivity, TeamPickerActivity::class.java)
            startActivity(intent)
            customType(this@LottieActivity, "fadein-to-fadeout")
            finish()
        }
    }
}

