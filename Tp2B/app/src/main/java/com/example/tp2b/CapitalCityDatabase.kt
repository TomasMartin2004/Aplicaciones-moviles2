package com.example.tp2b

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tp2b.model.CapitalCity

@Database(entities = [CapitalCity::class], version = 2, exportSchema = false)
abstract class CapitalCityDatabase : RoomDatabase() {

    abstract fun capitalCityDao(): CapitalCityDao

    companion object {

        @Volatile
        private var INSTANCE: CapitalCityDatabase? = null

        fun getDatabase(context: Context): CapitalCityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CapitalCityDatabase::class.java,
                    "capital_city_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
