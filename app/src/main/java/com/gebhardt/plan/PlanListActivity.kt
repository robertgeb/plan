package com.gebhardt.plan

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.gebhardt.plan.plan.PlanContent
import kotlinx.android.synthetic.main.fragment_item.view.*

class PlanListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        findViewById<Button>(R.id.add_btn).setOnClickListener {
            createNewPlan()
        }
        initDb()
        initRecyclerView()
    }

    private fun initDb(){
        val db = PlanDbHelper(this)
        val projection = arrayOf<String>(PlanContract.PlanEntry._ID, PlanContract.PlanEntry.COLUMN_NAME_CONTENT)
        val result = db.readableDatabase.query(
                PlanContract.PlanEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null)
        while (result.moveToNext()){
            PlanContent.addItem(PlanContent.PlanItem(result.getString(result.getColumnIndex(PlanContract.PlanEntry.COLUMN_NAME_CONTENT))))
        }
        result.close()
    }

    private fun initRecyclerView(){
        val mRecyclerView = findViewById<RecyclerView>(R.id.list)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = PlanRecyclerViewAdapter(PlanContent.ITEMS, null)

        val mIth = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                        Log.w("ItemTouchHelper", "onMove")
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                        Log.w("ItemTouchHelper", "onSwiped")
                    }
                })
        mIth.attachToRecyclerView(mRecyclerView)
    }

    private fun createNewPlan(){
        val contentEditText = findViewById<EditText>(R.id.add_plan_tv)
        val content = contentEditText.text.toString()

        if (content == "")
            return

        contentEditText.setText("")

        val plan = PlanContent.PlanItem(content)
        PlanContent.addItem(plan)

        val db = PlanDbHelper(this)
        val values = ContentValues()
        values.put(PlanContract.PlanEntry.COLUMN_NAME_CONTENT, content)
        db.writableDatabase.insert(PlanContract.PlanEntry.TABLE_NAME, null, values)

        findViewById<RecyclerView>(R.id.list).adapter.notifyDataSetChanged();
    }
}
