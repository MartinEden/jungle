package me.edens.jungle.model.actions

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.ModelChange
import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.evidence.Evidence
import me.edens.jungle.model.evidence.withEvidence

abstract class ActorAction<TActor: Actor>(private val actor: TActor) : Action {
    override fun apply(model: Model): ModelChange {
        val actorChange = update(actor)
        val newModel = model.replaceActor(actor, actorChange.newActor)
        return newModel withEvidence actorChange.evidence
    }

    protected abstract fun update(actor: TActor): ActorChange
}

data class ActorChange(val newActor: Actor, val evidence: Evidence)

fun <TActor: Actor> TActor.withEvidence(evidenceFunc: (TActor) -> Evidence)
        = ActorChange(this, evidenceFunc(this))