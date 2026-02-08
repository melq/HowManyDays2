package com.github.melq.howmanydays.data.repository

import com.github.melq.howmanydays.data.dao.MilestoneDao
import com.github.melq.howmanydays.data.entity.Milestone
import kotlinx.coroutines.flow.Flow

interface IMilestoneRepository {
    suspend fun insert(milestone: Milestone)
    suspend fun delete(milestone: Milestone)
    fun getByDayInfoId(dayInfoId: Int): Flow<List<Milestone>>
    suspend fun getListByDayInfoId(dayInfoId: Int): List<Milestone>
    suspend fun update(milestone: Milestone)
}

class MilestoneRepository(private val milestoneDao: MilestoneDao) : IMilestoneRepository {
    override suspend fun insert(milestone: Milestone) = milestoneDao.insert(milestone)
    override suspend fun delete(milestone: Milestone) = milestoneDao.delete(milestone)
    override fun getByDayInfoId(dayInfoId: Int): Flow<List<Milestone>> =
            milestoneDao.getByDayInfoId(dayInfoId)
    override suspend fun getListByDayInfoId(dayInfoId: Int): List<Milestone> =
            milestoneDao.getListByDayInfoId(dayInfoId)
    override suspend fun update(milestone: Milestone) = milestoneDao.update(milestone)
}
