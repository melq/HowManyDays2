package com.github.melq.howmanydays.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.melq.howmanydays.data.dao.DayInfoDao
import com.github.melq.howmanydays.data.entity.Converters
import com.github.melq.howmanydays.data.entity.DayInfo

@Database(entities = [DayInfo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DayInfoDatabase : RoomDatabase() {
    abstract fun dayInfoDao(): DayInfoDao

    companion object {
        @Volatile
        private var Instance: DayInfoDatabase? = null

        fun getDatabase(context: Context): DayInfoDatabase {
            return Instance ?: synchronized(this) {
                return Room.databaseBuilder(
                    context,
                    DayInfoDatabase::class.java,
                    "day_info_database"
                ).build().also { Instance = it }
            }
        }
    }
}