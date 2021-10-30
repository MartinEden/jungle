package me.edens.jungle.model.actions

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.evidence.withNoEvidence

data class RemoveAction(val actor: Actor): Action {
    override fun apply(model: Model) = model.removeActor(actor).withNoEvidence()
}