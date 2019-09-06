package com.xau.todoapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.xau.todoapp.R
import com.xau.todoapp.database.DbOperationHelper
import com.xau.todoapp.util.Session
import com.xau.todoapp.util.data.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private data class ViewHolder(
            val emailTextInputLayout: TextInputLayout,
            val passwordTextInputLayout: TextInputLayout,

            val emailTextInputEditText: TextInputEditText,
            val passwordTextInputEditText: TextInputEditText,

            val enterButton: Button,
            val createAccountButton: Button
    )

    private lateinit var viewHolder: ViewHolder
    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.session = Session(this)

        this.viewHolder = ViewHolder(
            findViewById(R.id.text_input_layout_main_email),
            findViewById(R.id.text_input_layout_main_password),
            findViewById(R.id.text_input_edit_main_email),
            findViewById(R.id.text_input_edit_main_password),
            findViewById(R.id.button_main_enter),
            findViewById(R.id.button_main_createAccount)
        )

        this.viewHolder.createAccountButton.setOnClickListener { view ->
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }

        this.viewHolder.enterButton.setOnClickListener(this.EnterButtonOnClick)
    }

    override fun onResume() {
        super.onResume()
        this.checkLoggedAndOpenTodoActivity()
    }

    private fun checkLoggedAndOpenTodoActivity(): Unit {
        this.session.updateLogged()
        if (this.session.isLogged()) {
            Toast.makeText(this, getString(R.string.template_bem_vindo_user)
                .format(session.loggedUser?.name), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, TodoActivity::class.java))
        }
    }

    private val EnterButtonOnClick = View.OnClickListener { view ->
        if (this.checkInputs()) {
            val email:String = viewHolder.emailTextInputEditText.text.toString()
            val password:String = viewHolder.passwordTextInputEditText.text.toString()

            if (this.session.login(email, password)) {
                this.checkLoggedAndOpenTodoActivity()
            } else {
                viewHolder.emailTextInputLayout.error = getString(R.string.informacoes_incoretas)
            }
        }
    }

    private fun checkInputs():Boolean {
        val email:String = viewHolder.emailTextInputEditText.text.toString()
        val password:String = viewHolder.passwordTextInputEditText.text.toString()

        var isOkay = true

        if (email.isEmpty()) {
            isOkay = false
            viewHolder.emailTextInputLayout.error = getString(R.string.voce_precisa_digitar_o_email)
        } else {
            viewHolder.emailTextInputLayout.error = null
        }
        if (password.isEmpty()) {
            isOkay = false
            viewHolder.passwordTextInputLayout.error = getString(R.string.voce_precisa_digitar_a_senha)
        } else {
            viewHolder.passwordTextInputLayout.error = null
        }

        return isOkay
    }

}
