package me.edens.jungle.model.actions

import me.edens.jungle.model.*
import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.evidence.Evidence
import me.edens.jungle.model.evidence.MovementEvidence
import me.edens.jungle.model.evidence.withEvidence

open class MoveAction<TActor : Actor>(actor: TActor, private val target: Place)
    : SimpleActorAction<TActor>(actor) {

    @Suppress("UNCHECKED_CAST")
    override fun update(actor: TActor) = actor.atLocation(target) as TActor

    override fun evidence(newActor: TActor) = MovementEvidence(
            newActor,
            actor.location,
            newActor.location
    )
}

class HumanMoveAction(human: Human, transition: Transition)
    : MoveAction<Human>(human, transition.target), HumanAction {

    override val description = "Go ${transition.description} to ${transition.target}"
}