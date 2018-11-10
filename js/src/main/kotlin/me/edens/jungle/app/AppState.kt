package me.edens.jungle.app

import me.edens.jungle.model.InitialModelState
import me.edens.jungle.model.Model
import me.edens.jungle.model.evidence.Evidence

data class AppState(val model: Model, val feedback: List<Evidence> = emptyList())
{
    companion object {
        val initial = AppState(InitialModelState.model)
    }
}