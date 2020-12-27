package taki.eddine.premier.league.pro.ui.appui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import taki.eddine.premier.league.pro.databinding.ActivityRssBinding

class RssActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRssBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRssBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        intent?.let {
            val uri = it.getStringExtra("url")
            binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
            binding.webview.loadUrl(uri!!)
        }

    }
}