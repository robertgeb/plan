package com.gebhardt.plan

import android.provider.BaseColumns


object PlanContract {

    class PlanEntry : BaseColumns {
        companion object {
            val _ID = "id"
            val TABLE_NAME = "plan"
            val COLUMN_NAME_CONTENT = "content"
        }
    }
}