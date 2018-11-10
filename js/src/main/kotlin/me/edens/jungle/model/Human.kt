package me.edens.jungle.model

import me.edens.jungle.model.actions.DoNothingAction
import me.edens.jungle.model.actions.MoveAction

data class Human(override val location: Place): Thing {
    fun actions(model: Model): Sequence<Action> {
        return sequenceOf(DoNothingAction()) + movementActions(model)
    }

    private fun movementActions(model: Model): Sequence<MoveAction> {
        return model.map
                .getNeighbours(location)
                .asSequence()
                .map { transition -> MoveAction(transition) }
    }

    fun atLocation(newLocation: Place) = copy(location = newLocation)
}