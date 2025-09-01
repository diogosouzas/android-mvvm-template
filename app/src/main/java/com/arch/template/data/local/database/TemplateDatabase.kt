package com.arch.template.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.arch.template.data.local.dao.UserDao
import com.arch.template.data.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class TemplateDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    
    companion object {
        const val DATABASE_NAME = "template_database"
    }
}
