package com.saitejajanjirala.githubclone.utils


import androidx.appcompat.widget.SearchView
import io.reactivex.rxjava3.subjects.PublishSubject

class RxSearchObservable {
    companion object {
        fun fromView(searchView: SearchView): PublishSubject<String> {
            val subject = PublishSubject.create<String>()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    subject.onComplete()
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(text: String): Boolean {
                    subject.onNext(text)
                    return true
                }
            })
            return subject
        }
    }
}