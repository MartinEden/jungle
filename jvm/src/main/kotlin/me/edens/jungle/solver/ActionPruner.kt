package me.edens.jungle.solver

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.PigsPlace
import me.edens.jungle.model.actions.DropAction
import me.edens.jungle.model.actors.PigCarcass
import me.edens.jungle.model.items.*

class ActionPruner {
    fun allows(action: Action, model: Model) = when (action) {
        is DropAction -> allowsDrop(action, model)
        else -> true
    }

    private fun allowsDrop(action: DropAction, model: Model) = when (action.item) {
        is Mushroom -> model.human.location == PigsPlace
        is PigCarcass -> true
        else -> false
    }
}