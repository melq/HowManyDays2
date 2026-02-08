package com.github.melq.howmanydays.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.melq.howmanydays.data.dao.DayInfoDao
import com.github.melq.howmanydays.data.dao.MilestoneDao
import com.github.melq.howmanydays.data.entity.Converters
import com.github.melq.howmanydays.data.entity.DayInfo
import com.github.melq.howmanydays.data.entity.Milestone

@Database(entities = [DayInfo::class, Milestone::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DayInfoDatabase : RoomDatabase() {
    abstract fun dayInfoDao(): DayInfoDao
    abstract fun milestoneDao(): MilestoneDao

    companion object {
        @Volatile private var Instance: DayInfoDatabase? = null

        fun getDatabase(context: Context): DayInfoDatabase {
            return Instance
                    ?: synchronized(this) {
                        return Room.databaseBuilder(
                                        context,
                                        DayInfoDatabase::class.java,
                                        "day_info_database"
                                )
                                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                                .build()
                                .also { Instance = it }
                    }
        }

        private val MIGRATION_1_2 =
                object : Migration(1, 2) {
                    override fun migrate(db: SupportSQLiteDatabase) {
                        db.execSQL(
                                "CREATE TABLE IF NOT EXISTS `notified` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dayInfoId` INTEGER NOT NULL, `displayMode` TEXT NOT NULL, `milestone` INTEGER NOT NULL, `deleted` INTEGER NOT NULL)"
                        )
                    }
                }

        private val MIGRATION_2_3 =
                object : Migration(2, 3) {
                    override fun migrate(db: SupportSQLiteDatabase) {
                        db.execSQL(
                                "CREATE TABLE IF NOT EXISTS `milestone` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dayInfoId` INTEGER NOT NULL, `value` INTEGER NOT NULL, `isNotified` INTEGER NOT NULL, FOREIGN KEY(`dayInfoId`) REFERENCES `day_info`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )"
                        )
                        db.execSQL(
                                "CREATE INDEX IF NOT EXISTS `index_milestone_dayInfoId` ON `milestone` (`dayInfoId`)"
                        )
                    }
                }
    }
}
