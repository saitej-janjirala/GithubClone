package com.saitejajanjirala.githubclone.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.saitejajanjirala.githubclone.BaseApplication
import com.saitejajanjirala.githubclone.di.component.ActivityComponent
import com.saitejajanjirala.githubclone.di.component.DaggerActivityComponent
import com.saitejajanjirala.githubclone.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel,VB:ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    lateinit var binding:VB

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        val view=LayoutInflater.from(this).inflate(provideLayoutId(),null,false)
        initializeBinding(view)
        setContentView(binding.root)
        setUpView(savedInstanceState)
        setObservers()
        viewModel.onCreate()
    }

    protected abstract fun setUpView(savedInstanceState: Bundle?)

    protected open fun setObservers(){
        viewModel.messageString.observe(this, Observer {
            Log.i("toastcalled",it)
            showMessage(it)
        })
    }
    fun showMessage(message: String) =  Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()


    protected abstract fun initializeBinding(view: View)

    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as BaseApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

}