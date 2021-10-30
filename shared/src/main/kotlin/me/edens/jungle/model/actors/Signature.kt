package me.edens.jungle.model.actors

enum class Signature {
    Human,
    Pig,
    Monster,
    SightTrail
}

val actorOrder = listOf(
        Signature.Pig,
        Signature.Monster,
        Signature.SightTrail
)