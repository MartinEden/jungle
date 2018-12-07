package me.edens.jungle.model

import me.edens.jungle.model.actors.Signature

class PlaceTransitions(val source: Place, transitions: Set<Transition>) {
    val transitions = transitions.associateBy { it.target }
}

data class Transition(val description: String, val target: Place, val access: Access) {
    fun accessibleTo(signature: Signature): Boolean = when (access) {
        Access.Everyone -> true
        is Access.Just -> signature == access.allowed
    }
}

sealed class Access {
    object Everyone : Access()
    data class Just(val allowed: Signature): Access()
}

infix fun String.transitionTo(place: Place) = Transition(this, place, Access.Everyone)