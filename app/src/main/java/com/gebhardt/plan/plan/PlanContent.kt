package com.gebhardt.plan.plan

import com.gebhardt.plan.PlanDbHelper
import java.util.ArrayList
import java.util.HashMap

object PlanContent {

    val ITEMS: MutableList<PlanItem> = ArrayList()

    private val COUNT = 0

    init {
    }

    fun addItem(item: PlanItem) {
        ITEMS.add(item)
    }

    fun removeItem(item: PlanItem) {
        ITEMS.remove(item)
    }

    class PlanItem(val id: Int, val content: String) {

        override fun toString(): String {
            return content
        }
    }
}
