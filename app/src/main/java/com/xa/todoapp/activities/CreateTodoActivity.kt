package com.xau.todoapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.xau.todoapp.R
import com.xau.todoapp.database.DbOperationHelper
import com.xau.todoapp.util.Session
import com.xau.todoapp.util.data.Todo
import com.xau.todoapp.util.time.Data
import com.xau.todoapp.util.time.DataInvalidaException
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class CreateTodoActivity : AppCompatActivity() {

    private data class ViewHolder(
        val titleTextInputLayout: TextInputLayout,
        val dataTextInputLayout: TextInputLayout,

        val titleTextInputEditText: TextInputEditText,
        val dataTextInputEditText: TextInputEditText,

        val priorityRadioGroup: RadioGroup,

        val highRadioButton: RadioButton,
        val mediumRadioButton: RadioButton,
        val lowRadioButton: RadioButton,

        val saveButton: Button,

        val toolbar: Toolbar
    )

    private lateinit var viewHolder:ViewHolder
    private lateinit var session: Session
    private lateinit var dbOperationHelper: DbOperationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        this.session = Session(this)
        this.dbOperationHelper = DbOperationHelper(this)

        this.viewHolder = ViewHolder(
            findViewById(R.id.text_input_layout_create_todo_title),
            findViewById(R.id.text_input_layout_create_todo_data),

            findViewById(R.id.text_input_edit_create_todo_title),
            findViewById(R.id.text_input_edit_create_todo_data),

            findViewById(R.id.radio_group_create_todo_priority),

            findViewById(R.id.radio_button_create_todo_high),
            findViewById(R.id.radio_button_create_todo_medium),
            findViewById(R.id.radio_button_create_todo_low),

            findViewById(R.id.button_create_todo_save),

            findViewById(R.id.toolbar_create_todo_toolbar)
        )

        setSupportActionBar(viewHolder.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewHolder.lowRadioButton.isChecked = true

        viewHolder.saveButton.setOnClickListener(this.SaveOnClick)
    }

    private val SaveOnClick = View.OnClickListener { view ->
        if (this.checkInputs()) {
            val title = viewHolder.titleTextInputEditText.text.toString()
            val dataString = viewHolder.dataTextInputEditText.text.toString()
            val data:String
            try {
                data = Data.formatData(dataString).formatDataUniversal

                val priority: Int = this.getPriority()

                val todo = Todo(null, title, data, priority, false,
                    session.loggedUser?.id!!)

                if (dbOperationHelper.addTodo(todo) != null) {
                    this.finish()
                } else {
                    Toast.makeText(this, getString(R.string.ocorreu_algum_erro),
                        Toast.LENGTH_LONG).show()
                }

            } catch (e: DataInvalidaException) {
                viewHolder.dataTextInputLayout.error = e.message
            }
        }
    }

    private fun checkInputs(): Boolean {
        val title = viewHolder.titleTextInputEditText.text.toString()
        val data = viewHolder.dataTextInputEditText.text.toString()

        var isOkay = true

        if (title.isEmpty()) {
            viewHolder.titleTextInputLayout.error = getString(
                R.string.voce_precisa_informar_o_titulo)
            isOkay = false
        } else {
            viewHolder.titleTextInputLayout.error = null
        }

        if (data.isEmpty()) {
            viewHolder.dataTextInputLayout.error = getString(R.string.voce_precisa_informar_a_data)
            isOkay = false
        } else {
            viewHolder.dataTextInputLayout.error = null
        }

        return isOkay
    }

    private fun getPriority(): Int {
        return when (viewHolder.priorityRadioGroup.checkedRadioButtonId) {
            R.id.radio_button_create_todo_high -> {
                2
            }
            R.id.radio_button_create_todo_medium -> {
                1
            }
            else -> {
                0
            }
        }
    }



}
