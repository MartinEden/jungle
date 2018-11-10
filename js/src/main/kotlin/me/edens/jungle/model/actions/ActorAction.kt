package me.edens.jungle.model.actions

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.ModelChange
import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.evidence.Evidence
import me.edens.jungle.model.evidence.withEvidence

abstract class ActorAction<TActor: Actor, TNewActor: Actor> : Action {
    abstract val actor: TActor

    override fun apply(model: Model): ModelChange {
        val newActor = update(actor)
        val newModel = model.replaceActor(actor, newActor)
        return newModel withEvidence evidence(newActor)
    }

    protected abstract fun update(actor: TActor): TNewActor
    protected abstract fun evidence(newActor: TNewActor): Evidence
}

abstract class SimpleActorAction<TActor: Actor>(override val actor: TActor)
    : ActorAction<TActor, TActor>()