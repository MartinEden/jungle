package me.edens.jungle.model.actions

import me.edens.jungle.model.Place
import me.edens.jungle.model.actors.MoveableActor
import me.edens.jungle.model.evidence.AudioEvidence
import me.edens.jungle.model.evidence.EvidenceGroup

data class NoisyMoveAction<TActor : MoveableActor>(
        val actor: TActor,
        val target: Place,
        val noise: String
) : MoveAction<TActor>(actor, target) {

    override fun evidence() = EvidenceGroup(listOf(
            super.evidence(),
            AudioEvidence(noise, target)
    ))
}