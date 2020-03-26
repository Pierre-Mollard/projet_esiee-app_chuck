package com.creepierre.chucknorrisjoke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

@kotlinx.serialization.UnstableDefault
class MainActivity : AppCompatActivity() {

    val compositeDisposable:CompositeDisposable = CompositeDisposable()
    val jokeService:JokeApiService = JokeApiServiceFactory.createJokeApiService()
    val adapter = JokeAdapter()
    var progressBarView:ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Display the joke list on the Logcat
        JokeManager.printAllJokes()

        //RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBarView = findViewById<ProgressBar>(R.id.progressBar)

        recyclerView.adapter = adapter

        newJoke()
    }

    fun onClickNewJoke(pView: View){
        Log.i("JOKEMANAGER", "Button clicked ! (id:${pView.id})")
        newJoke()
    }

    fun newJoke(){
        val joke:Single<Joke> = jokeService.giveMeAJoke()
        progressBarView?.visibility = View.VISIBLE
        compositeDisposable.add(joke.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { Log.i("JOKEMANAGER", "new joke from api : ${it.value}")
                    adapter.addJoke(it)
                    progressBarView?.visibility = View.INVISIBLE
                    compositeDisposable.clear()},
                onError = { Log.e("JOKEMANAGER", "ERROR API")
                    progressBarView?.visibility = View.INVISIBLE
                    compositeDisposable.clear()}
            ))
    }
}
