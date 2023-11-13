package com.estudos.estatistica.ui.features.home

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.estudos.estatistica.R
import com.estudos.estatistica.model.actions.ActionHome
import com.estudos.estatistica.model.actions.Action

class HomeViewModel(
    resources: Resources
) : ViewModel() {

    val listOfActions = listOf(
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
        ),
        Action(
            name = resources.getString(R.string.historic_action),
            action = ActionHome.HISTORIC
        )
    )

}