package com.estudos.estatistica.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.estudos.estatistica.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Action(
    val name: String,
    val action: ActionHome
) : Parcelable {

    @get:StringRes
    val nameForHeaderTable: Int
        get() {
            return if(action == ActionHome.CONTINUOUS_DATA){
                R.string.table_type_continuous_data
            } else {
                R.string.table_type_val
            }
        }
}

@Parcelize
enum class ActionHome : Parcelable {
    CONTINUOUS_DATA, DISCRETE_DATA, UNGROUPED_DISCRETE_DATA
}
