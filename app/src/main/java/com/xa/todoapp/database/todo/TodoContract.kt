package com.xau.todoapp.database.todo

import android.provider.BaseColumns
import com.xau.todoapp.database.user.UserContract

object TodoContract {
    object TodoEntry : BaseColumns {
        const val TABLE_NAME = "todo"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DONE = "done"
        const val COLUMN_NAME_DATA = "data"
        const val COLUMN_NAME_PRIORITY = "priority"
        const val COLUMN_NAME_USER_ID = "user_id"
    }

    const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TodoEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TodoEntry.COLUMN_NAME_TITLE} TEXT," +
                    "${TodoEntry.COLUMN_NAME_DONE} INTEGER," +
                    "${TodoEntry.COLUMN_NAME_DATA} TEXT," +
                    "${TodoEntry.COLUMN_NAME_PRIORITY} INTEGER," +
                    "${TodoEntry.COLUMN_NAME_USER_ID} INTEGER," +
                    "FOREIGN KEY(${TodoEntry.COLUMN_NAME_USER_ID}) REFERENCES" +
                    " ${UserContract.UserEntry.TABLE_NAME}(${BaseColumns._ID}))"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TodoEntry.TABLE_NAME}"
}