package me.edens.jungle.model

import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.evidence.Evidence
import me.edens.jungle.model.evidence.TextEvidence

interface ActorAction {
    fun apply(change: ModelChange): ModelChange
}

class IsolatedActorAction<TActor: Actor>(
        private val actor: TActor,
        private val evidence: Evidence,
        private val func: (TActor) -> TActor
): ActorAction {
    override fun apply(change: ModelChange) = ModelChange(
            change.newModel.updateActor(actor, func),
            change.evidence + evidence
    )
}

class GlobalActorAction(
        private val evidence: Evidence,
        private val func: (Model) -> Model
): ActorAction {
    override fun apply(change: ModelChange) = ModelChange(
            func(change.newModel),
            change.evidence + evidence
    )
}

fun <TActor: Actor> TActor.update(
        evidence: String,
        func: (TActor) -> TActor
) = IsolatedActorAction(this, TextEvidence(evidence, this.location), func)

fun Actor.updateModel(
        evidence: String,
        func: (Model) -> Model
) = GlobalActorAction(TextEvidence(evidence, this.location), func)

