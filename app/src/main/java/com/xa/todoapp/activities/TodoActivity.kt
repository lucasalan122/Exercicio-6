package com.xau.todoapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xau.todoapp.R
import com.xau.todoapp.database.DbOperationHelper
import com.xau.todoapp.util.Session
import com.xau.todoapp.util.data.Todo
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoActivity : AppCompatActivity() {

    private data class ViewHolder(
        val todoListRecyclerView: RecyclerView,
        val toolbar: Toolbar,
        val addTodoFloatingActionButton: FloatingActionButton
    )

    private class RecyclerAdapter(private var todos: Array<Todo>) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

        class ViewHolder(val todos: Array<Todo>, itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

            val titleTextView:TextView = itemView.findViewById(R.id.text_list_todo_title)
            val dataTextView:TextView = itemView.findViewById(R.id.text_list_todo_data)
            val doneCheckBox:CheckBox = itemView.findViewById(R.id.checkbox_list_todo_done)

            init {
                doneCheckBox.setOnClickListener(this)
            }

            override fun onClick(view: View?) {
                val position:Int = adapterPosition
                when (view?.id) {
                    R.id.checkbox_list_todo_done -> {
                        val todo:Todo = this.todos[position]
                        val isDone:Boolean = doneCheckBox.isChecked
                        updateCheckTodo(todo, isDone, view.context)
                    }
                }
            }
            private fun updateCheckTodo(todo: Todo, isDone:Boolean, context: Context) {
                val dbOperationHelper = DbOperationHelper(context)
                dbOperationHelper.updateTodoIsDone(todo, isDone)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                RecyclerAdapter.ViewHolder {
            return ViewHolder(todos, LayoutInflater.from(parent.context)
                .inflate(R.layout.list_todo_view, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.titleTextView.text = todos[position].title
            holder.dataTextView.text = todos[position].data
            holder.doneCheckBox.isChecked = todos[position].isDone
        }

        fun updateValues(todos: Array<Todo>) {
            this.todos = todos
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = todos.size
    }

    private lateinit var viewHolder: ViewHolder
    private lateinit var dbOperationHelper: DbOperationHelper
    private lateinit var session: Session

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        this.dbOperationHelper = DbOperationHelper(this)
        this.session = Session(this)

        this.viewHolder = ViewHolder(
            findViewById(R.id.recycler_todo_todo_list),
            findViewById(R.id.toolbar_todo_toolbar),
            findViewById(R.id.fab_todo_add_todo)
        )

        setSupportActionBar(viewHolder.toolbar)
        viewHolder.addTodoFloatingActionButton.setOnClickListener(this.fabAddTodoOnClick)

        viewManager = LinearLayoutManager(this)

        val todos:Array<Todo> = dbOperationHelper.getTodos(session.loggedUser!!)

        viewAdapter = RecyclerAdapter(todos)

        viewHolder.todoListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    override fun onResume() {
        super.onResume()
        this.updateTodoList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_todo_itens, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_todo_logout -> {
            this.session.logout()
            this.close()
            true
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private val fabAddTodoOnClick = View.OnClickListener { view ->
        startActivity(Intent(this, CreateTodoActivity::class.java))
    }

    private fun updateTodoList() {
        (this.viewAdapter as RecyclerAdapter).updateValues(
            this.dbOperationHelper.getTodos(this.session.loggedUser!!)
        )
    }

    private fun close() {
        this.finish()
    }
}
