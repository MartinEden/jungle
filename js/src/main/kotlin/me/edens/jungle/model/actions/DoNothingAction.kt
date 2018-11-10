package me.edens.jungle.model.actions

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.ModelChange

class DoNothingAction : Action {
    override val description = "Do nothing"

    override fun apply(model: Model) = ModelChange(model, emptyList())
}