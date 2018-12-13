package me.edens.jungle.model.actions.monster

import me.edens.jungle.model.Action
import me.edens.jungle.model.Model
import me.edens.jungle.model.actors.Monster
import me.edens.jungle.model.actors.PigCarcass
import me.edens.jungle.model.evidence.AudioEvidence
import me.edens.jungle.model.evidence.EvidenceGroup
import me.edens.jungle.model.evidence.VisualEvidence
import me.edens.jungle.model.evidence.withEvidence

class EatCarcassAction(private val monster: Monster, private val carcass: PigCarcass) : Action {
    override fun apply(model: Model) = model
            .removeItem(carcass)
            .replaceActor(monster, monster.copy(hungry = false))
            .withEvidence(EvidenceGroup(listOf(
                    VisualEvidence("The creature tears into the carcass, and gulps it down hungrily", carcass.location),
                    AudioEvidence("loud munching and tearing sounds", carcass.location)
            )))
}
