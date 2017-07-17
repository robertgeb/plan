package com.gebhardt.plan

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.gebhardt.plan.plan.PlanContent
import kotlinx.android.synthetic.main.activity_main.*



class PlanListActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        // Configurando eventos
        initEvents()

        // Recuperando dados do banco
        PlanContent.ITEMS.addAll(PlanDbHelper(this).findAll())

        // Criando View
        initRecyclerView()
    }

    private fun initEvents() {
        // Criar novo ao clicar no botao
        add_btn.setOnClickListener {
            createNewPlan()
        }

        // Adicionar ao clicar Enter no teclado
        add_plan_tv.setOnEditorActionListener { textView, i, keyEvent -> createNewPlan() }

        // Deletar ao mover para direita
        val mIth = ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {
                    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                        Log.w("ItemTouchHelper", "onMove")
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                        val planViewHolder = viewHolder as PlanRecyclerViewAdapter.ViewHolder
                        deletePlan(planViewHolder.plan)
                    }
                })

        mIth.attachToRecyclerView(list_rv)
    }

    private fun initRecyclerView(){
        list_rv.setHasFixedSize(true)
        list_rv.layoutManager = LinearLayoutManager(this)
        list_rv.adapter = PlanRecyclerViewAdapter(PlanContent.ITEMS, null)
    }

    private fun createNewPlan() : Boolean{
        val content = add_plan_tv.text.toString()
        // Se não tiver nada escrito
        if (content == "")
            return false

        // Esvazia a caixa de texto
        add_plan_tv.setText("")

        // Adiciona no banco de dados
        val plan = PlanDbHelper(this).insert(content)

        // Adiciona na lista de itens
        PlanContent.addItem(plan)

        // Atualiza a view
        list_rv.adapter.notifyDataSetChanged()
        return true
    }

    private fun deletePlan(plan : PlanContent.PlanItem?){
        if(plan == null)
            return

        // Removendo do banco de dados
        PlanDbHelper(this).delete(plan)
        // Removendo da lista de itens
        PlanContent.removeItem(plan)
        // Atualizando View
        list_rv.adapter.notifyDataSetChanged()
    }
}
