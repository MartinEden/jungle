package me.edens.jungle.model.actors

import me.edens.jungle.model.*
import me.edens.jungle.model.actions.DoNothingAction
import me.edens.jungle.model.actions.MoveAction
import me.edens.jungle.model.evidence.AudioEvidence
import me.edens.jungle.model.evidence.withEvidence
import me.edens.jungle.model.items.BasicItem

data class Pig(
        override val location: Place
) : BasicActor(Signature.Pig), MoveableActor, ICanWound {
    override fun atLocation(location: Place) = Pig(location)

    override fun act(model: Model): Action {
        return if (location == PigsPlace) {
            if (anyThreatsPresents(model)) {
                MoveAction(this, PigsPlace.Undergrowth)
            } else {
                SnuffleAction(location)
            }
        } else {
            if (anyThreatsPresents(model)) {
                SnuffleAction(location)
            } else {
                MoveAction(this, PigsPlace)
            }
        }
    }

    private fun anyThreatsPresents(model: Model) = model.actorsAt(PigsPlace).any { it != this }

    data class SnuffleAction(val place: Place) : Action {
        override fun apply(model: Model) = model.withEvidence(
                AudioEvidence("snuffling and oinking", place)
        )
    }
}

data class PigCarcass(
        override val location: Place
) : BasicItem("A dead forest pig", location) {
    override fun atLocation(place: Place) = PigCarcass(place)
}