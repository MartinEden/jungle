package me.edens.jungle.model.items

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place

data class Parachute(override val location: Place) : Item {
    override val id = "parachute"

    override fun affordances(state: Model): Sequence<Action> {
        return this.moveableAffordances(state)
    }

    override fun atLocation(place: Place): Item {
        return copy(location = place)
    }

    override fun toString() = "parachute"
}