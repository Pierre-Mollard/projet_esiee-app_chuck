package com.creepierre.chucknorrisjoke

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter(pOnBottomReached: (() -> Unit)) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    val onBottomReached: (() -> Unit) = pOnBottomReached

    private var listJokes:MutableList<Joke> = mutableListOf<Joke>()
        set(newListJokes) {
            field=newListJokes
            notifyDataSetChanged()
        }

    fun addJoke(pJoke: Joke){
        listJokes.add(pJoke)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_textview, parent, false) as TextView)
    }

    override fun getItemCount(): Int {
        return listJokes.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.joke.text = listJokes[position].value
        Log.i("JOKEMANAGER", "BIND VIEW ==== (pos:${position})")
        if(listJokes.size > 4 && position == itemCount-1){
            onBottomReached()
        }
    }

    class JokeViewHolder(val joke: TextView) : RecyclerView.ViewHolder(joke){

    }

    fun List<String>.toJokeList():List<Joke> {
        return this.map { jokeString -> Joke(categories = emptyList(), createdAt = "", updatedAt = "", url = "", id = "", iconUrl = "", value = jokeString) }
    }
}