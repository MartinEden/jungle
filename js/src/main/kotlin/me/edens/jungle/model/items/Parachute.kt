package me.edens.jungle.model.items

import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place
import me.edens.jungle.model.evidence.withNoEvidence

class Parachute(location: Place) : BasicItem("Parachute", location) {
    override fun atLocation(place: Place): Item {
        return Parachute(location = place)
    }
}

class ParachuteStrips(location: Place) : BasicItem("Strips of parachute cloth", location) {
    override fun affordances(state: Model): Sequence<Action> {
        return super.affordances(state) + sequenceOf(TieStripsIntoRopeAction(this))
    }

    override fun atLocation(place: Place): Item {
        return ParachuteStrips(location = place)
    }

    class TieStripsIntoRopeAction(private val strips: ParachuteStrips) : Action {
        override val description = "Tie the parachute strips together to make a rough rope"

        override fun apply(model: Model) = model.updateItem(strips) {
            Rope(Inventory)
        }.withNoEvidence()
    }
}