package me.edens.jungle.model.actors

import me.edens.jungle.model.*
import me.edens.jungle.model.actions.DoNothingAction
import me.edens.jungle.model.actions.MoveAction
import me.edens.jungle.model.evidence.AudioEvidence
import me.edens.jungle.model.evidence.VisualEvidence
import me.edens.jungle.model.evidence.withEvidence
import me.edens.jungle.model.items.BasicItem
import me.edens.jungle.model.items.Mushroom

data class Pig(
        override val location: Place
) : BasicActor(Signature.Pig), MoveableActor, ICanWound {
    override fun atLocation(location: Place) = Pig(location)

    override fun act(model: Model): Action {
        val mushroom = model.itemsAt<Mushroom>(PigsPlace).singleOrNull()

        return if (location == PigsPlace) {
            when {
                mushroom != null -> EatAction(mushroom)
                anyThreatsPresents(model) -> MoveAction(this, PigsPlace.Undergrowth)
                else -> SnuffleAction(location)
            }
        } else {
            when {
                mushroom != null -> MoveAction(this, PigsPlace)
                anyThreatsPresents(model) -> SnuffleAction(location)
                else -> MoveAction(this, PigsPlace)
            }
        }
    }

    private fun anyThreatsPresents(model: Model) = model.actorsAt<Actor>(PigsPlace).any { it != this }

    data class SnuffleAction(val place: Place) : Action {
        override fun apply(model: Model) = model.withEvidence(
                AudioEvidence("snuffling and oinking", place)
        )
    }
    class EatAction(private val mushroom: Mushroom) : Action {
        override fun apply(model: Model) = model.removeItem(mushroom).withEvidence(VisualEvidence(
                "The pig tucks eagerly into the mushroom, gobbling it down quickly",
                mushroom.location
        ))
    }
}

class PigCarcass(location: Place) : BasicItem("A dead forest pig", location) {
    override fun atLocation(place: Place) = PigCarcass(place)
}