package me.edens.jungle.model.items

import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place

data class Knife(override val location: Place) : Item {
    override fun affordances(state: Model): Sequence<Action> {
        val item = this
        return sequence {
            yieldAll(item.moveableAffordances(state))
            state.withIfPresent<Parachute> {
                yield(CutParachuteIntoStripsAction(it))
            }
        }
    }

    override fun atLocation(place: Place): Item {
        return copy(location = place)
    }

    override fun toString() = "Knife"

    class CutParachuteIntoStripsAction(private val parachute: Parachute) : Action {
        override val description = "Use the knife to cut the parachute into strips"

        override fun apply(model: Model) = model.updateItem(parachute) {
            ParachuteStrips(Inventory)
        }
    }
}