package com.xau.todoapp.database.user

import android.provider.BaseColumns

object UserContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "user"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_PASSWORD = "password"
    }

    const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${UserEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${UserEntry.COLUMN_NAME_NAME} TEXT," +
                    "${UserEntry.COLUMN_NAME_EMAIL} TEXT," +
                    "${UserEntry.COLUMN_NAME_PASSWORD} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME}"
}