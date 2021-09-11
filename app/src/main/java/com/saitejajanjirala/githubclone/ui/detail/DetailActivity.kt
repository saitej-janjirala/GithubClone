package com.saitejajanjirala.githubclone.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.saitejajanjirala.githubclone.R
import com.saitejajanjirala.githubclone.adapter.ContributorAdapter
import com.saitejajanjirala.githubclone.base.BaseActivity
import com.saitejajanjirala.githubclone.databinding.ActivityDetailBinding
import com.saitejajanjirala.githubclone.di.component.ActivityComponent
import com.saitejajanjirala.githubclone.models.Contributor
import com.saitejajanjirala.githubclone.models.Item
import com.saitejajanjirala.githubclone.ui.webview.WebViewActivity
import com.saitejajanjirala.githubclone.utils.Keys

class DetailActivity : BaseActivity<DetailViewModel, ActivityDetailBinding>() {
    private lateinit var item: Item
    private lateinit var contributorAdapter: ContributorAdapter
    private lateinit var contributors: List<Contributor>

    override fun setUpView(savedInstanceState: Bundle?) {
        item = (intent?.getParcelableExtra<Item>(Keys.ITEM) ?: finish()) as Item
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(item.owner!!.avatar_url)
                .error(R.drawable.ic_baseline_error_24)
                .into(avatar)
            repoName.text = "RepoName:- ${item.name}"
            userName.text = "UserName:- ${item.owner?.login}"
            repoStars.text = "Stars:- ${item?.stargazers_count}"
            repoForks.text = "Forks:- ${item.forks}"
            repoIssues.text = "Open issues:- ${item.open_issues}"
            description.text = "$description"
            val url = "<u>${item.html_url}</u>"
            projectLink.text = HtmlCompat.fromHtml(url, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        contributors = mutableListOf()
        contributorAdapter = ContributorAdapter(this)
        binding.contributorsRecyclerView.adapter = contributorAdapter
        callContributorsApi()
    }

    private fun callContributorsApi() {
        item.name?.let { item.owner?.login?.let { it1 -> viewModel.getContributors(it1, it) } }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.contributorList.observe(this, Observer {
            contributors = it
            contributorAdapter.submitList(contributors)
        })
        viewModel.isLoading.observe(this, Observer {
            it?.let { x ->
                if (x) {
                    binding.progressbar.visibility = View.VISIBLE
                } else {
                    binding.progressbar.visibility = View.GONE
                }
            }
        })
        viewModel.error.observe(this, Observer {
            it?.let { x ->
                binding.retryLayout.isVisible=x
                binding.contributorsRecyclerView.isVisible=!x
            }
        })
        binding.retryButton.setOnClickListener {
            callContributorsApi()
        }
        binding.projectLink.setOnClickListener {
            val intent= Intent(this@DetailActivity,WebViewActivity::class.java)
            intent.putExtra(Keys.URL,item.html_url)
            startActivity(intent)
        }

    }

    override fun initializeBinding(view: View) {
        binding = ActivityDetailBinding.bind(view)
    }

    override fun provideLayoutId() = R.layout.activity_detail

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

}