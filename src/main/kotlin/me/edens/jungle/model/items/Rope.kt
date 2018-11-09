package me.edens.jungle.model.items

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place

data class Rope(override val location: Place): Item {
    override fun affordances(state: Model): Sequence<Action> {
        return moveableAffordances(state)
    }

    override fun atLocation(place: Place) = copy(location = location)

    override fun toString() = "A rough rope made of strips of parachute cloth"
}