package com.example.lab7.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab7.utilities.Converters
import com.example.lab7.dao.CardDao
import com.example.lab7.models.Card


@Database(entities = [Card::class], version = 2)
@TypeConverters(Converters::class)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        @Volatile
        private var INSTANCE: CardDatabase? = null

        fun getInstance(context: Context): CardDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): CardDatabase {
            return Room.databaseBuilder(context.applicationContext,
                CardDatabase::class.java, "cardDatabase")
                 .fallbackToDestructiveMigration()
                .build()
        }
    }
}
