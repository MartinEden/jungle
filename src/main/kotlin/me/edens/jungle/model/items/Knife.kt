package me.edens.jungle.model.items

import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place

class Knife(location: Place) : BasicItem("Knife", location) {
    override fun affordances(state: Model): Sequence<Action> {
        return super.affordances(state) + sequence {
            state.withIfPresent<Parachute> {
                yield(CutParachuteIntoStripsAction(it))
            }
        }
    }

    override fun atLocation(place: Place): Item {
        return Knife(location = place)
    }

    class CutParachuteIntoStripsAction(private val parachute: Parachute) : Action {
        override val description = "Use the knife to cut the parachute into strips"

        override fun apply(model: Model) = model.updateItem(parachute) {
            ParachuteStrips(Inventory)
        }
    }
}