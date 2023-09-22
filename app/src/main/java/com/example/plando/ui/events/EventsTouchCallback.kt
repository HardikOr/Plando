package com.example.plando.ui.events

import android.graphics.Canvas
import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.plando.R
import kotlin.math.min
import kotlin.math.sign

class EventsTouchCallback(val fragment: EventsFragment, val deleteEvent: (Int) -> Unit) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        deleteEvent(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        canvas: Canvas,
        rv: RecyclerView,
        vh: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        fragment.apply {
            val itemView = vh.itemView
//            val deltaX = dX / rv.width
//            val isToRight = deltaX >= 0F

            // Background
            R.drawable.card_background.getDrawable()?.apply {
                setTint(android.R.color.holo_red_light.getColor())
                setBounds(itemView.left, itemView.top, itemView.right, itemView.bottom)
                draw(canvas)
            }

            // delete icon
            drawIcon(R.drawable.icon_delete, itemView, canvas)

            val nextDX = min(rv.width.toFloat() / 2, sign(dX) * dX) * sign(dX)
            super.onChildDraw(canvas, rv, vh, nextDX, dY, actionState, isCurrentlyActive)
        }
    }

    fun drawIcon(
        @DrawableRes id: Int,
        view: View,
        canvas: Canvas,
        isRightSide: Boolean = true,
        scale: Double = 1.5
    ) {
        fragment.apply {
            id.getDrawable()?.apply {
                val w = this.intrinsicWidth
                val h = this.intrinsicHeight
                val middle = (view.top + view.bottom) / 2

                if (isRightSide) {
                    setBounds(
                        (view.right.toDouble() - (1 + scale) * w).toInt(),
                        (middle.toDouble() - h * scale / 2).toInt(),
                        (view.right.toDouble() - w).toInt(),
                        (middle.toDouble() + h * scale / 2).toInt()
                    )
                } else {
                    setBounds(
                        (view.left.toDouble() + w).toInt(),
                        (middle.toDouble() - h * scale / 2).toInt(),
                        (view.left.toDouble() + (1 + scale) * w).toInt(),
                        (middle.toDouble() + h * scale / 2).toInt()
                    )
                }
                draw(canvas)
            }
        }
    }
}

