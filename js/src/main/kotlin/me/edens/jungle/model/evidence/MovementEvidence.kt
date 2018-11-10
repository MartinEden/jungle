package me.edens.jungle.model.evidence

import me.edens.jungle.model.Place
import me.edens.jungle.model.Thing

data class MovementEvidence(
        val actor: Thing,
        override val source: Place,
        val destination: Place
) : Evidence