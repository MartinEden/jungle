package me.edens.jungle.model.evidence

import me.edens.jungle.model.Model
import me.edens.jungle.model.ModelChange
import me.edens.jungle.model.actors.Signature

interface Evidence {
    val subject: Signature
    fun apparentTo(observer: Observer): Boolean
}

infix fun Model.withEvidence(evidence: Evidence) = ModelChange(this, listOf(evidence))
fun Model.withNoEvidence() = ModelChange(this, emptyList())