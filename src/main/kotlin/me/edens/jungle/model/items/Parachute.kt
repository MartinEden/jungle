package me.edens.jungle.model.items

import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place

data class Parachute(override val location: Place) : Item {
    override fun affordances(state: Model): Sequence<Action> {
        return this.moveableAffordances(state)
    }

    override fun atLocation(place: Place): Item {
        return copy(location = place)
    }

    override fun toString() = "Parachute"
}

data class ParachuteStrips(override val location: Place): Item {
    override fun affordances(state: Model): Sequence<Action> {
        val item = this
        return sequence {
            yieldAll(item.moveableAffordances(state))
            yield(TieStripsIntoRopeAction(item))
        }
    }

    override fun atLocation(place: Place): Item {
        return copy(location = place)
    }

    override fun toString() = "Strips of parachute cloth"

    class TieStripsIntoRopeAction(private val strips: ParachuteStrips) : Action {
        override val description = "Tie the parachute strips together to make a rough rope"

        override fun apply(model: Model) = model.updateItem(strips) {
            Rope(Inventory)
        }
    }
}