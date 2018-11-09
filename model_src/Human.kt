package me.edens.jungle.model

data class Human(val location: Place) {
    fun actions(model: Model): Iterable<Action> {
        return model.map.getNeighbours(location).map { transition -> MoveAction(transition) }
    }

    fun atLocation(newLocation: Place) = copy(location = newLocation)
}