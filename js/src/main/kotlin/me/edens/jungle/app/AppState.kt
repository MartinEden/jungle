package me.edens.jungle.app

import me.edens.jungle.model.InitialModelState
import me.edens.jungle.model.Model

data class AppState(val model: Model, val feedback: List<String> = emptyList())
{
    companion object {
        val initial = AppState(InitialModelState.model)
    }
}