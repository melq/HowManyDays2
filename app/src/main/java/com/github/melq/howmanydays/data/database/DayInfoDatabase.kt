package com.github.melq.howmanydays.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.melq.howmanydays.data.dao.DayInfoDao
import com.github.melq.howmanydays.data.dao.NotifiedDao
import com.github.melq.howmanydays.data.entity.Converters
import com.github.melq.howmanydays.data.entity.DayInfo
import com.github.melq.howmanydays.data.entity.Notified

@Database(entities = [DayInfo::class, Notified::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DayInfoDatabase : RoomDatabase() {
    abstract fun dayInfoDao(): DayInfoDao
    abstract fun notifiedDao(): NotifiedDao

    companion object {
        @Volatile
        private var Instance: DayInfoDatabase? = null

        fun getDatabase(context: Context): DayInfoDatabase {
            return Instance ?: synchronized(this) {
                return Room.databaseBuilder(
                    context,
                    DayInfoDatabase::class.java,
                    "day_info_database"
                ).addMigrations(MIGRATION_1_2).build().also { Instance = it }
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `notified` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dayInfoId` INTEGER NOT NULL, `displayMode` TEXT NOT NULL, `milestone` INTEGER NOT NULL, `deleted` INTEGER NOT NULL)")
            }

        }
    }
}