package me.edens.jungle.app

import me.edens.jungle.model.InitialModelState
import me.edens.jungle.model.Model

data class AppState(val model: Model)
{
    companion object {
        val initial = AppState(InitialModelState.model)
    }
}