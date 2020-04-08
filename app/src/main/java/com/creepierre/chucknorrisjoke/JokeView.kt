package com.creepierre.chucknorrisjoke

import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class JokeView(context: Context) : @JvmOverloads ConstraintLayout(context){

    data class Model(
        val textView : TextView,
        val starBT : ImageButton,
        val shareBT : ImageButton)

    var onBTshareClicked: ((Joke) -> Unit) = {}
    var onBTstarClicked: ((Joke) -> Unit) = {}
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view = inflater.inflate(R.layout.joke_layout, this, true)
    val model = Model(view.findViewById(R.id.textView), view.findViewById(R.id.starBT), view.findViewById(R.id.shareBT))
    var joke: Joke = Joke(emptyList(), "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP")

    fun setupView(model: Model){
        val textView : TextView = view.findViewById(R.id.textView)
        val starBT : ImageButton = view.findViewById(R.id.starBT)
        val shareBT : ImageButton = view.findViewById(R.id.shareBT)
        val prefs = context.getSharedPreferences("Prefs_ChuckNorrisJoke", Context.MODE_PRIVATE)

        textView.text = joke.value
        shareBT.setOnClickListener { onBTshareClicked(joke) }
        starBT.setOnClickListener {
            onBTstarClicked(joke)
            checkStar(prefs, starBT)
        }
        checkStar(prefs, starBT)
    }

    fun checkStar(prefs: SharedPreferences, starBT: ImageButton){
        if(prefs.contains(joke.id)){
            starBT.setImageResource(R.drawable.ic_star_on)
        }else{
            starBT.setImageResource(R.drawable.ic_star_off)
        }
    }
}