package com.creepierre.chucknorrisjoke

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.*
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json

class JokeAdapter(pOnBottomReached: (() -> Unit), pOnBTshareClicked: ((Joke) -> Unit), pOnBTstarClicked: ((Joke) -> Unit)) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    val onBottomReached: (() -> Unit) = pOnBottomReached
    val onBTshareClicked: ((Joke) -> Unit) = pOnBTshareClicked
    val onBTstarClicked: ((Joke) -> Unit) = pOnBTstarClicked
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

    fun removeJoke(pJoke: Joke){
        listJokes.remove(pJoke)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(JokeView(parent.context))
    }

    override fun getItemCount(): Int {
        return listJokes.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.jokeView.joke = listJokes[position]
        holder.jokeView.onBTshareClicked = onBTshareClicked
        holder.jokeView.onBTstarClicked = onBTstarClicked
        holder.jokeView.setupView(holder.jokeView.model)

        Log.i("JOKEMANAGER", "BIND VIEW ==== (pos:${position})")
        if(listJokes.size > 4 && position == itemCount-1){
            onBottomReached()
        }
    }

    class JokeViewHolder(val jokeView: JokeView) : RecyclerView.ViewHolder(jokeView){
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