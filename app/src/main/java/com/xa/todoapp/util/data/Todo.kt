package com.xau.todoapp.util.data

data class Todo(val id: Long?, val title: String, val data:String, val priority: Int,
                val isDone: Boolean, val userId: Long)