package me.edens.jungle.model.actors

import me.edens.jungle.model.Model
import me.edens.jungle.model.Place
import me.edens.jungle.model.actions.RemoveAction

data class SightTrail(
        override val signature: Signature,
        override val location: Place,
        val target: Place
) : Actor {
    val source = location

    override fun act(model: Model) = RemoveAction(this)
}

sealed class Sight {
    object DidNotSee : Sight()
    data class Saw(val place: Place): Sight()
}