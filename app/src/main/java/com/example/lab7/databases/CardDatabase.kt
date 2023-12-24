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
        private var cardDatabase: CardDatabase? = null
        fun getInstance(context: Context): CardDatabase {
            synchronized(this) {
                var databaseInstance = cardDatabase
                if (databaseInstance == null) {
                    databaseInstance = Room.databaseBuilder(
                        context, CardDatabase::class.java, "cardDatabase"
                    ).fallbackToDestructiveMigration().build()
                    cardDatabase = databaseInstance
                }
                return databaseInstance
            }
        }
    }
}