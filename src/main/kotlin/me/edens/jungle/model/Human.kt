package me.edens.jungle.model

import me.edens.jungle.model.actions.MoveAction

data class Human(val location: Place) {
    fun actions(model: Model): Sequence<Action> {
        return model.map
                .getNeighbours(location)
                .asSequence()
                .map { transition -> MoveAction(transition) }
    }

    fun atLocation(newLocation: Place) = copy(location = newLocation)
}