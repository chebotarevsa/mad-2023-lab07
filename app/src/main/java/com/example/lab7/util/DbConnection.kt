package com.example.lab7.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab7.db.dao.CardDao
import com.example.lab7.db.entity.CardEntity


@Database(entities = [CardEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class DbConnection : RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        private var cardDatabase: DbConnection? = null
        fun getInstance(context: Context): DbConnection {
            synchronized(this) {
                var databaseInstance = cardDatabase
                if (databaseInstance == null) {
                    databaseInstance = Room.databaseBuilder(
                        context, DbConnection::class.java, "cardDatabase"
                    ).fallbackToDestructiveMigration().build()
                    cardDatabase = databaseInstance
                }
                return databaseInstance
            }
        }
    }
}