package com.github.melq.howmanydays.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.melq.howmanydays.data.entity.Milestone
import kotlinx.coroutines.flow.Flow

@Dao
interface MilestoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(milestone: Milestone)

    @Delete suspend fun delete(milestone: Milestone)

    @Query("SELECT * FROM milestone WHERE dayInfoId = :dayInfoId")
    fun getByDayInfoId(dayInfoId: Int): Flow<List<Milestone>>

    @Query("SELECT * FROM milestone WHERE dayInfoId = :dayInfoId")
    suspend fun getListByDayInfoId(dayInfoId: Int): List<Milestone>

    @Update suspend fun update(milestone: Milestone)
}
