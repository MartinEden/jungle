package me.edens.jungle.model.actions

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.Transition

class MoveAction(val transition: Transition) : Action {
    override val description = "Go ${transition.description} to ${transition.target}"

    override fun apply(model: Model)
            = model.run { copy(human = human.atLocation(transition.target)) }
}