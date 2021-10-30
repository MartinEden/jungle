package me.edens.jungle.model.evidence

import me.edens.jungle.model.Human
import me.edens.jungle.model.Map
import me.edens.jungle.model.Model
import me.edens.jungle.model.ModelChange

interface Evidence {
    fun apparentTo(observer: Human, map: Map): Boolean
    fun describe(observer: Human, map: Map): String
}

infix fun Model.withEvidence(evidence: Evidence) = ModelChange(this, listOf(evidence))
fun Model.withNoEvidence() = ModelChange(this, emptyList())