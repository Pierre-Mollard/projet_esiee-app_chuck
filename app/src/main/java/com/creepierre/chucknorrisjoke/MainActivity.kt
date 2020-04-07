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
    val adapter = JokeAdapter(pOnBottomReached = {newJoke(10)}, pOnBTshareClicked = {onJokeShared(joke = it)}, pOnBTstarClicked = {onJokeStared(joke = it)})
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
        val jokeTouchHelper = JokeTouchHelper(onItemMoved = ::onItemMoved, onJokeRemoved = ::onJokeRemoved).attachToRecyclerView(recyclerView)

        if (savedInstanceState != null) {
            adapter.unserializeList(list = savedInstanceState.getString("jokeList"))
            Log.i("SERIALIZATION", "Reloaded successfully")
        }else{
            newJoke(1)
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("jokeList", adapter.serializeList())
        Log.i("SERIALIZATION", "Loaded successfully")
        super.onSaveInstanceState(outState)
    }

    fun onClickNewJoke(pView: View){
        Log.i("JOKEMANAGER", "Button clicked ! (id:${pView.id})")
        newJoke(1)
    }

    fun onJokeStared(joke: Joke){
        Log.i("JOKEMANAGER", "Joke stared ! (id:${joke.id})")
        joke.stared = !joke.stared
    }

    fun onJokeShared(joke: Joke){
        Log.i("JOKEMANAGER", "Joke shared ! (id:${joke.id})")
    }

    fun onItemMoved(viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder){
        Log.i("JOKEMANAGER", "item moved (wtf?)")
        //dont know how to do it and what it means
    }

    fun onJokeRemoved(viewHolder: RecyclerView.ViewHolder){
        val jokeViewHolder = viewHolder as JokeAdapter.JokeViewHolder
        adapter.removeJoke(jokeViewHolder.jokeView.joke)
    }

    fun newJoke(n: Long){
        Log.i("JOKEMANAGER", "new joke requested")
        val joke: Single<Joke> = jokeService.giveMeAJoke()
        progressBarView?.visibility = View.VISIBLE
        compositeDisposable.add(joke.repeat(n).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    Log.i("JOKEMANAGER", "all jokes requested arrived")
                    progressBarView?.visibility = View.INVISIBLE
                    compositeDisposable.clear()
                },
                onError = {
                    Log.e("JOKEMANAGER", "ERROR API")
                    progressBarView?.visibility = View.INVISIBLE
                    compositeDisposable.clear()
                },
                onNext = {
                    Log.i("JOKEMANAGER", "new joke from api : ${it.value}")
                    adapter.addJoke(it)
                }
            ))
    }
}
