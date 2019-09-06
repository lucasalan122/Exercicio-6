package com.xau.todoapp.util

import android.content.Context
import android.content.SharedPreferences
import com.xau.todoapp.database.DbOperationHelper
import com.xau.todoapp.util.data.User

class Session(context: Context) {
    private val PREFS_NAME = "Session"
    private val LOGGED_ID = "loggedId"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME,
        Context.MODE_PRIVATE)
    private val dbOperationHelper: DbOperationHelper = DbOperationHelper(context)

    var loggedUser:User? = this.update()

    fun login(email: String, password: String): Boolean {
        val user:User? = dbOperationHelper.getUser(email, password)

        return if (user != null) {
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.putLong(LOGGED_ID, user.id!!)
            editor.commit()

            this.loggedUser = user

            true
        } else {
            false
        }
    }

    fun signIn(user: User): Boolean {
        val users = dbOperationHelper.getUsers(user.email)
        return if (users.size > 0) {
            false
        } else {
            dbOperationHelper.addUser(user)
            // this maybe return true if all be correct
            return this.login(user.email, user.password)
        }
    }

    fun logout():Unit {
        val editor:SharedPreferences.Editor = sharedPreferences.edit()

        editor.remove(LOGGED_ID)
        editor.commit()

         this.loggedUser = this.update()
    }

    fun isLogged():Boolean = this.loggedUser != null

    public fun updateLogged() {
        this.loggedUser = this.update()
    }

    private fun update():User? {
        val id:Long = this.sharedPreferences.getLong(LOGGED_ID, -1)

        return if (id == -1L) {
            null
        } else {
            this.dbOperationHelper.getUser(id)
        }
    }
}