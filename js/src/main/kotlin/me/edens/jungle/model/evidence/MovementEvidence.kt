package me.edens.jungle.model.evidence

import me.edens.jungle.model.Human
import me.edens.jungle.model.Place
import me.edens.jungle.model.actors.Signature

data class MovementEvidence(
        val subject: Signature,
        val source: Place,
        val destination: Place
) : Evidence {
    override fun apparentTo(observer: Human): Boolean {
        return subject != Signature.Human
            && (observer.location == source || observer.location == destination)
    }

    override fun describe(observer: Human) = "$subject moves from $source to $destination"
}