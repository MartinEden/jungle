package me.edens.jungle.model.actions

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model

class DoNothingAction : Action {
    override val description = "Do nothing"

    override fun apply(model: Model) = model
}