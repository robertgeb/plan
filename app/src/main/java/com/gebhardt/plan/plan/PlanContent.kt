package com.gebhardt.plan.plan

import com.gebhardt.plan.PlanDbHelper
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 */
object PlanContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<PlanItem> = ArrayList()

    private val COUNT = 0

    init {
    }

    fun addItem(item: PlanItem) {
        ITEMS.add(item)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    class PlanItem(val content: String) {

        override fun toString(): String {
            return content
        }
    }
}
