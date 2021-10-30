package me.edens.jungle.model.actions.monster

import me.edens.jungle.model.*
import me.edens.jungle.model.actors.FireBreath
import me.edens.jungle.model.actors.Monster
import me.edens.jungle.model.evidence.AudioEvidence
import me.edens.jungle.model.evidence.EvidenceGroup
import me.edens.jungle.model.evidence.VisualEvidence
import me.edens.jungle.model.evidence.withEvidence


class BreathFireAction(private val monster: Monster, private val target: Place) : Action {
    override fun apply(model: Model): ModelChange {
        var newModel = model.replaceActor(monster, monster.copy(breath = FireBreath.NotReady))
        if (newModel.human.location == target) {
            newModel = newModel.copy(status = Status.Death)
        }
        return newModel withEvidence EvidenceGroup(listOf(
                VisualEvidence("The monster breaths fire on you, killing you", target),
                AudioEvidence("the monster's flames whoosh in the jungle behind you", target)
        ))
    }
}