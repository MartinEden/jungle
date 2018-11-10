package me.edens.jungle.model.actions

import me.edens.jungle.model.Model
import me.edens.jungle.model.ModelChange

class DoNothingAction : HumanAction {
    override val description = "Do nothing"
    override fun apply(model: Model) = ModelChange(model, emptyList())
}