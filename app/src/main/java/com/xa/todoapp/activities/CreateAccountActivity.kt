package com.xau.todoapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.xau.todoapp.R
import com.xau.todoapp.util.Session
import com.xau.todoapp.util.data.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CreateAccountActivity : AppCompatActivity() {

    private data class ViewHolder(
            val completeNameTextInputLayout: TextInputLayout,
            val emailTextInputLayout: TextInputLayout,
            val passwordTextInputLayout: TextInputLayout,
            val confirmPasswordTextInputLayout: TextInputLayout,

            val completeNameTextInputEditText: TextInputEditText,
            val emailTextInputEditText: TextInputEditText,
            val passwordTextInputEditText: TextInputEditText,
            val confirmPasswordTextInputEditText: TextInputEditText,

            val createButton: Button,

            val toolbar: Toolbar
    )

    private lateinit var viewHolder:ViewHolder
    private lateinit var session:Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        this.session = Session(this)

        this.viewHolder = ViewHolder(
            findViewById(R.id.text_input_layout_create_account_complete_name),
            findViewById(R.id.text_input_layout_create_account_email),
            findViewById(R.id.text_input_layout_create_account_password),
            findViewById(R.id.text_input_layout_create_account_confirm_password),

            findViewById(R.id.text_input_edit_create_account_complete_name),
            findViewById(R.id.text_input_edit_create_account_email),
            findViewById(R.id.text_input_edit_create_account_password),
            findViewById(R.id.text_input_edit_create_account_confirm_password),

            findViewById(R.id.button_create_account_create),

            findViewById(R.id.toolbar_create_account_toolbar)
        )

        setSupportActionBar(viewHolder.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewHolder.createButton.setOnClickListener(this.createButtonOnClick)
    }

    private val createButtonOnClick = View.OnClickListener { view ->
        if (this.checkInputs() && this.checkPasswords()) {
            val completeName:String = viewHolder.completeNameTextInputEditText.text.toString()
            val email:String = viewHolder.emailTextInputEditText.text.toString()
            val password:String = viewHolder.passwordTextInputEditText.text.toString()

            val user = User(null, completeName, email, password)

            if (session.signIn(user)) {
                this.close()
            } else {
                viewHolder.emailTextInputLayout.error = getString(
                    R.string.ja_existe_esse_email_cadastrado)
            }
        }
    }

    private fun checkInputs():Boolean {
        val completeName:String = viewHolder.completeNameTextInputEditText.text.toString()
        val email:String = viewHolder.emailTextInputEditText.text.toString()
        val password:String = viewHolder.passwordTextInputEditText.text.toString()
        val confirmPassword:String = viewHolder.confirmPasswordTextInputEditText.text.toString()

        var isOkay = true

        if (completeName.isEmpty()) {
            viewHolder.completeNameTextInputLayout.error = getString(
                R.string.voce_precisa_digitar_o_seu_nome)
            isOkay = false
        } else {
            viewHolder.completeNameTextInputLayout.error = null
        }

        if (email.isEmpty()) {
            viewHolder.emailTextInputLayout.error = getString(R.string.voce_precisa_digitar_o_email)
            isOkay = false
        } else {
            viewHolder.emailTextInputLayout.error = null
        }

        if (password.isEmpty()) {
            viewHolder.passwordTextInputLayout.error = getString(
                R.string.voce_precisa_digitar_a_senha)
            isOkay = false
        } else {
            viewHolder.passwordTextInputLayout.error = null
        }

        if (confirmPassword.isEmpty()) {
            viewHolder.confirmPasswordTextInputLayout.error = getString(
                R.string.voce_precisa_confirmar_a_senha)
            isOkay = false
        } else {
            viewHolder.confirmPasswordTextInputLayout.error = null
        }

        return isOkay
    }

    private fun checkPasswords():Boolean {
        val password:String = viewHolder.passwordTextInputEditText.text.toString()
        val confirmPassword:String = viewHolder.confirmPasswordTextInputEditText.text.toString()

        if (password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (password != confirmPassword) {
                viewHolder.confirmPasswordTextInputLayout.error = getString(
                    R.string.as_senhas_nao_sao_iguais)
                return false
            } else {
                viewHolder.confirmPasswordTextInputLayout.error = null
                return true
            }
        } else {
            return false
        }
    }

    private fun close() {
        this.finish()
    }
}
