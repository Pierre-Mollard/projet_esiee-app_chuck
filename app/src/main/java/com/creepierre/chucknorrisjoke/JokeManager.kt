package com.creepierre.chucknorrisjoke

import android.util.Log

object JokeManager{

    val listJokes:MutableList<String> = mutableListOf<String>("Chuck Norris doesn't buy wine. He just stares at a bottle of Welches Grape Juice and it turns into 1945 Chateau Mouton Rothschild out of fear.",
        "As a kid, Chuck Norris's dog didn't eat his homework, his homework ate his dog",
        "Chuck Norris can spike a volly ball... under handed",
        "Chuck Norris uses ribbed condoms inside out, so he gets the pleasure.",
        "Chuck Norris cleans his toilet with a backhoe and a WeedEater.",
        "Chuck Norris knows more about propaganda then Noam Chomsky.",
        "Odin, king of the viking gods, hung himself for 9 days and survived. Chuck Norris has made fun of him ever since for being a pussy and not going longer.",
        "Chuck Norris does not have any hair on his testicles. Hair doesn't grow on steel.",
        "Chuck Norris' driver's license simply shows his shoe size.",
        "When Super Saiyans get angry they transform into Chuck Norris.")

    fun printAllJokes(){
        listJokes.forEach { Log.i("JOKEMANAGER", "joke n°${listJokes.indexOf(it)+1}: ${it}") }
    }

}