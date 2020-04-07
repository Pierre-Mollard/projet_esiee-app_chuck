package com.creepierre.chucknorrisjoke

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class JokeTouchHelper(
    private val onJokeRemoved: ((RecyclerView.ViewHolder) -> Unit) ,
    private val onItemMoved: ((RecyclerView.ViewHolder, RecyclerView.ViewHolder) -> Unit)
) : ItemTouchHelper(
    object : ItemTouchHelper.SimpleCallback(
        UP or DOWN,
        LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            onItemMoved(viewHolder, target)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            if(swipeDir == LEFT || swipeDir == RIGHT){
                onJokeRemoved(viewHolder)
            }
        }
    }
)