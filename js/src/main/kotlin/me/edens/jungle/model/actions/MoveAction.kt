package me.edens.jungle.model.actions

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.ModelChange
import me.edens.jungle.model.Transition
import me.edens.jungle.model.evidence.MovementEvidence
import me.edens.jungle.model.evidence.withEvidence

class MoveAction(val transition: Transition) : Action {
    override val description = "Go ${transition.description} to ${transition.target}"

    override fun apply(model: Model): ModelChange {
        val newModel = model.run { copy(human = human.atLocation(transition.target)) }
        return newModel withEvidence MovementEvidence(
                newModel.human,
                model.human.location,
                newModel.human.location
        )
    }
}