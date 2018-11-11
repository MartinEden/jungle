package me.edens.jungle.model.evidence

import me.edens.jungle.model.Place
import me.edens.jungle.model.actors.Actor

data class MovementEvidence<TActor: Actor>(
        override val source: Place,
        val destination: Place
) : Evidence