package com.gebhardt.plan

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.gebhardt.plan.PlanFragment.OnListFragmentInteractionListener
import com.gebhardt.plan.plan.PlanContent.PlanItem

/**
 * [RecyclerView.Adapter] that can display a [PlanItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class PlanRecyclerViewAdapter(private val mValues: List<PlanItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<PlanRecyclerViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.plan = mValues[position]
        //holder.mIdView.text = mValues[position].id
        holder.mContentView.text = mValues[position].content

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.plan!!)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView
        var plan: PlanItem? = null

        init {
            //mIdView = mView.findViewById<View>(R.id.id) as TextView
            mContentView = mView.findViewById<View>(R.id.content) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
