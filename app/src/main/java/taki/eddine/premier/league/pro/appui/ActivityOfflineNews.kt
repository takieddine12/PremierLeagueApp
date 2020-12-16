package taki.eddine.premier.league.pro.appui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import taki.eddine.premier.league.pro.databinding.ActivityOfflineNewsBinding


class ActivityOfflineNews : AppCompatActivity() {
    private lateinit var binding : ActivityOfflineNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOfflineNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        intent?.let {
            binding.offlinedate.text = it.getStringExtra("offlineDate")
            binding.offlinetitle.text = it.getStringExtra("offlineTitle")
            binding.offlinedescription.text = it.getStringExtra("offlineDescription")
            binding.bitmap.setImageBitmap(it.getParcelableExtra("offlineImage"))
        }
    }
}