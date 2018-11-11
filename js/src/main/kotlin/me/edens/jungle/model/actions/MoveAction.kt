package me.edens.jungle.model.actions

import me.edens.jungle.model.*
import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.evidence.MovementEvidence

open class MoveAction<TActor : Actor>(actor: TActor, private val target: Place)
    : ActorAction<TActor>(actor) {

    override fun update(actor: TActor) = actor.atLocation(target).withEvidence {
        MovementEvidence<TActor>(actor.location, it.location)
    }
}

class HumanMoveAction(human: Human, transition: Transition)
    : MoveAction<Human>(human, transition.target), HumanAction {

    override val description = "Go ${transition.description} to ${transition.target}"
}