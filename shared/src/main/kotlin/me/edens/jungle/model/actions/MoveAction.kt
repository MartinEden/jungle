package me.edens.jungle.model.actions

import me.edens.jungle.model.*
import me.edens.jungle.model.actors.MoveableActor
import me.edens.jungle.model.actors.SightTrail
import me.edens.jungle.model.evidence.Evidence
import me.edens.jungle.model.evidence.MovementEvidence
import me.edens.jungle.model.evidence.withEvidence

open class MoveAction<TActor : MoveableActor>(
        private val actor: TActor,
        private val target: Place
) : Action {
    override fun apply(model: Model): ModelChange = model
            .replaceActor(actor, actor.atLocation(target))
            .addActor(SightTrail(actor.signature, actor.location, target))
            .withEvidence(evidence())

    protected open fun evidence(): Evidence = MovementEvidence(actor.signature, actor.location, target)
}

class HumanMoveAction(human: Human, transition: Transition)
    : MoveAction<Human>(human, transition.target), HumanAction {

    override val description = "Go ${transition.description} to ${transition.target}"
}