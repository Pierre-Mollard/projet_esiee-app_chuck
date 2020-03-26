package com.creepierre.chucknorrisjoke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Display the joke list on the Logcat
        JokeManager.printAllJokes()

        //RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = JokeAdapter()
        recyclerView.adapter = adapter

        val jokeService:JokeApiService = JokeApiServiceFactory.createJokeApiService()
        val joke:Single<Joke> = jokeService.giveMeAJoke()

       joke.subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { Log.i("JOKEMANAGER", "joke api : ${it.value}") },
                onError = { Log.e("JOKEMANAGER", "ERROR API") }
            )

    }



}
