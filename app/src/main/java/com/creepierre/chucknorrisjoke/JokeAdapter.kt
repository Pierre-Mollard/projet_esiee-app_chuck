package com.creepierre.chucknorrisjoke

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JokeAdapter : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>() {

    var listJokes = JokeManager.listJokes
        set(newListJokes) {
            field=newListJokes
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_textview, parent, false) as TextView)
    }

    override fun getItemCount(): Int {
        return listJokes.size
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.joke.text = listJokes[position]
    }

    class JokeViewHolder(val joke: TextView) : RecyclerView.ViewHolder(joke){

    }
}