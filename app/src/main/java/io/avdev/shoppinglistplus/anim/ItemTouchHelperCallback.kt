package io.avdev.shoppinglistplus.anim

import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.avdev.shoppinglistplus.R
import io.avdev.shoppinglistplus.adapter.ShoppingListAdapter
import java.lang.NullPointerException

class ItemTouchHelperCallback : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        val itemView = viewHolder.itemView
        val translationX = itemView.translationX

        if (translationX != 0f) {
            animateTranslationX(viewHolder, translationX, 0f)
        }
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        val maxSwipeDistance = convertDpToPx(80).toFloat()

        if (dX < -maxSwipeDistance) {
            super.onChildDraw(c, recyclerView, viewHolder, -maxSwipeDistance, dY, actionState, isCurrentlyActive)
            drawButton(c,recyclerView,viewHolder,dX)

        } else if (dX > maxSwipeDistance) {
            super.onChildDraw(c, recyclerView, viewHolder, maxSwipeDistance, dY, actionState, isCurrentlyActive)
            drawButton(c,recyclerView,viewHolder,dX)
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            drawButton(c,recyclerView,viewHolder,dX)
        }
    }

    private fun animateTranslationX(viewHolder: RecyclerView.ViewHolder, startTranslationX: Float, endTranslationX: Float) {
        val animator = ValueAnimator.ofFloat(startTranslationX, endTranslationX).apply {
            duration = 500
            addUpdateListener { valueAnimator ->
                val animatedValue = valueAnimator.animatedValue as Float
                viewHolder.itemView.translationX = animatedValue
            }
            start()
        }
    }
    private fun drawButton(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw the background
        val background = ColorDrawable(Color.TRANSPARENT)
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right + dX.toInt() - 100, itemView.bottom)
        background.draw(c)

        // Create the button
        val button = Button(recyclerView.context)
        button.text = "Button"
        val buttonWidth = 200 // Установите ширину кнопки
        val buttonHeight = itemHeight // Подстроить высоту кнопки на основе элемента списка
        val buttonRight = itemView.right + dX.toInt()
        val buttonTop = itemView.top
        val buttonLeft = buttonRight - buttonWidth
        val buttonBottom = buttonTop + buttonHeight
        button.layout(buttonLeft, buttonTop, buttonRight, buttonBottom)
        button.draw(c)

        // Назначить слушатель нажатия кнопки
        button.setOnClickListener {
            throw NullPointerException()
        }

    }
    private fun convertDpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}