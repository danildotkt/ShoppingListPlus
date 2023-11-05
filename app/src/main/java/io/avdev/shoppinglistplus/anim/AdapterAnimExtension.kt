package io.avdev.shoppinglistplus.anim

import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.RecyclerView

class AdapterAnimExtension {

    fun animHolderElement() : TranslateAnimation{
        val animation = TranslateAnimation(0f, -80f, 0f, 0f)
        animation.duration = Long.MAX_VALUE
        animation.fillAfter = true
        return animation
    }

    fun animateViewHolder(viewHolder: RecyclerView.ViewHolder) {
        viewHolder.itemView.startAnimation(animHolderElement())
    }
}