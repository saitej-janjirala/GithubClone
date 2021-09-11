package com.saitejajanjirala.githubclone.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.saitejajanjirala.githubclone.R
import com.saitejajanjirala.githubclone.databinding.ActivityWebViewBinding
import com.saitejajanjirala.githubclone.utils.Keys
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    private lateinit var url:String
    private lateinit var binding:ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view=LayoutInflater.from(this).inflate(R.layout.activity_web_view,null,false)
        binding= ActivityWebViewBinding.bind(view)
        setContentView(binding.root)
        url= (intent.getStringExtra(Keys.URL)?:finish()) as String
        binding.webView.loadUrl(url)
        binding.webView.canGoBack()
        binding.webView.settings.javaScriptEnabled=true
        binding.webView.webViewClient=object: WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                binding.progressbar.visibility=View.VISIBLE
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                binding.progressbar.visibility=View.GONE
            }
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK && web_view.canGoBack()){
            binding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}