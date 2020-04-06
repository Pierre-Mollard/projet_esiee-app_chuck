package com.creepierre.chucknorrisjoke

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.*
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.json
import java.util.function.Consumer

class JokeAdapter(pOnBottomReached: (() -> Unit)) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    val onBottomReached: (() -> Unit) = pOnBottomReached
    @UseExperimental(kotlinx.serialization.ImplicitReflectionSerializer::class)
    val serializer: KSerializer<List<Joke>> = Joke::class.serializer().list

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

    fun serializeList(): String{
        return Json.stringify(serializer, listJokes)
    }

    fun unserializeList(list: String){
        val listSerial = Json.parse(serializer, list)
        listJokes.clear()
        listSerial.forEach({ listJokes.add(it) })
    }
}