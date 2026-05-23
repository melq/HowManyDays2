package com.github.melq.howmanydays.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.melq.howmanydays.data.Const.Companion.NEW_DAY_INFO_ID
import com.github.melq.howmanydays.data.DisplayMode
import com.github.melq.howmanydays.data.entity.DayInfo
import com.github.melq.howmanydays.data.entity.Milestone
import com.github.melq.howmanydays.data.repository.IDayInfoRepository
import com.github.melq.howmanydays.data.repository.IMilestoneRepository
import java.time.LocalDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HowManyDaysViewModel(
        private val dayInfoRepository: IDayInfoRepository,
        private val milestoneRepository: IMilestoneRepository
) : ViewModel() {
    private val _title = mutableStateOf("")
    private val _date = mutableStateOf(LocalDateTime.now())
    private val _displayMode = mutableStateOf(DisplayMode.DAYS)
    private val _selectedDayInfo = mutableStateOf(null as DayInfo?)
    private val _dayInfos = mutableStateOf(emptyList<DayInfo>())

    private val _milestones = mutableStateListOf<Milestone>()
    val milestones: List<Milestone>
        get() = _milestones
    private val _deletedMilestones = mutableListOf<Milestone>()

    val title: State<String> = _title
    val date: State<LocalDateTime> = _date
    val displayMode: State<DisplayMode> = _displayMode
    val selectedDayInfo: State<DayInfo?> = _selectedDayInfo
    val dayInfos: State<List<DayInfo>> = _dayInfos

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setDate(date: LocalDateTime) {
        _date.value = date
    }

    fun setDisplayMode(displayMode: DisplayMode) {
        _displayMode.value = displayMode
    }

    fun setSelectedDayInfo(dayInfo: DayInfo) {
        _selectedDayInfo.value = dayInfo
    }

    fun clearSelectedDayInfo() {
        _selectedDayInfo.value = null
        _milestones.clear()
        _deletedMilestones.clear()
    }

    fun setParametersByDayInfo(dayInfo: DayInfo) {
        setTitle(dayInfo.title)
        setDate(dayInfo.date)
        setDisplayMode(dayInfo.displayMode)
        fetchMilestones(dayInfo.id)
    }

    fun getCurrentDayInfoId(): Int {
        return _selectedDayInfo.value?.id ?: NEW_DAY_INFO_ID
    }

    fun fetchDayInfos() {
        viewModelScope.launch(Dispatchers.IO) {
            _dayInfos.value = dayInfoRepository.getAllDayInfos()
        }
    }

    private fun fetchMilestones(dayInfoId: Int) {
        viewModelScope.launch {
            _milestones.clear()
            _deletedMilestones.clear()
            _milestones.addAll(milestoneRepository.getListByDayInfoId(dayInfoId))
        }
    }

    fun addMilestone(value: Long) {
        _milestones.add(Milestone(dayInfoId = getCurrentDayInfoId(), value = value))
    }

    fun deleteMilestone(milestone: Milestone) {
        if (milestone.id != 0) {
            _deletedMilestones.add(milestone)
        }
        _milestones.remove(milestone)
    }

    suspend fun saveDayInfoWithMilestones() {
        val dayInfo = DayInfo(
                id = getCurrentDayInfoId(),
                title = _title.value,
                date = _date.value,
                displayMode = _displayMode.value
        )
        val dayInfoId =
                if (dayInfo.id == -1) {
                    dayInfoRepository.insertDayInfo(dayInfo).toInt()
                } else {
                    dayInfoRepository.updateDayInfo(dayInfo)
                    dayInfo.id
                }

        _deletedMilestones.forEach { milestoneRepository.delete(it) }
        _deletedMilestones.clear()

        _milestones.forEach { milestone ->
            if (milestone.id == 0) {
                milestoneRepository.insert(milestone.copy(dayInfoId = dayInfoId))
            } else {
                milestoneRepository.update(milestone)
            }
        }
        fetchDayInfos()
    }

    suspend fun deleteDayInfo() {
        _selectedDayInfo.value?.let {
            dayInfoRepository.deleteDayInfo(it)
        }
    }
}
