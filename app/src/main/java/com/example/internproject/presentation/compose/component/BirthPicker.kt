package com.example.internproject.presentation.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DisplayMode.Companion.Picker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun BirthPicker(
    date: LocalDate,
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    visibleItemNum: Int = 3,
    dividerColor: Color = Color.Black,
    onItemChanged: (LocalDate) -> Unit
) {

    val selectedYear = remember { mutableStateOf(date.year) }
    val yearList = remember { ((date.year - 70)..date.year).toList().reversed() }

    val selectedMonth = remember { mutableStateOf(date.monthValue ) }
    val monthList = remember { (1..12).toList() }

    val selectedDay = remember { mutableStateOf(date.dayOfMonth) }
    val dayList = remember {
        derivedStateOf {
            (1 ..YearMonth.of(selectedYear.value, selectedMonth.value).lengthOfMonth()).toList()
        }
    }

    fun getSelectedDate(): LocalDate {
        if(dayList.value.last() < selectedDay.value) {
            selectedDay.value = dayList.value.last()
        }

        return LocalDate.of(
            selectedYear.value, selectedMonth.value, selectedDay.value
        )
    }

    Row(horizontalArrangement = Arrangement.SpaceEvenly)  {
        NumberPicker(
            items = yearList,
            defaultState = selectedYear.value,
            modifier = itemModifier.weight(1f),
            visibleItemsCount = visibleItemNum,
            dividerColor = dividerColor
        ) {
            selectedYear.value = it
            onItemChanged(getSelectedDate())
        }


        NumberPicker(
            items = monthList,
            defaultState = selectedMonth.value,
            modifier = itemModifier.weight(1f),
            visibleItemsCount = visibleItemNum,
            dividerColor = dividerColor
        ) {
            selectedMonth.value = it
            onItemChanged(getSelectedDate())
        }

        NumberPicker(
            items = dayList.value,
            defaultState = selectedDay.value,
            modifier = itemModifier.weight(1f),
            visibleItemsCount = visibleItemNum,
            dividerColor = dividerColor
        ) {
            selectedDay.value = it
            onItemChanged(getSelectedDate())
        }
    }
}