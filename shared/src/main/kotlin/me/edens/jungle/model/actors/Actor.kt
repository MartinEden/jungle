package me.edens.jungle.model.actors

import me.edens.jungle.model.*

interface Actor : Thing {
    val signature: Signature
    fun act(model: Model): Action
}

interface MoveableActor : Actor {
    fun atLocation(location: Place): Actor
}

abstract class BasicActor(override val signature: Signature) : StructuralEqualityBase(), Actor {
    override fun fieldsAreEqual(other: Any): Boolean {
        val x = other as BasicActor
        return signature == x.signature
                && location == x.location
    }
}

fun Actor.getSightOf(subject: Actor, model: Model): Sight = if (subject.location == location) {
    Sight.Saw(subject.location)
} else {
    val sightTrail = model.actors
            .filterIsInstance<SightTrail>()
            .singleOrNull { it.source == location && it.targetSignature == subject.signature }
    if (sightTrail != null) {
        Sight.Saw(sightTrail.target)
    } else {
        Sight.DidNotSee
    }
}