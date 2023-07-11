package com.estudos.estatistica.ui.home

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.estudos.estatistica.R
import com.estudos.estatistica.model.ActionHome
import com.estudos.estatistica.model.Action

class HomeViewModel(
    resources: Resources
): ViewModel() {

    val _listBtnActions = listOf(
        Action(
            name = resources.getString(R.string.continuous_data),
            action = ActionHome.CONTINUOUS_DATA
        ),
        Action(
            name = resources.getString(R.string.discrete_data),
            action = ActionHome.DISCRETE_DATA
        ),
        Action(
            name = resources.getString(R.string.ungrouped_discrete_data),
            action = ActionHome.UNGROUPED_DISCRETE_DATA
        )
    )

}