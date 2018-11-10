package me.edens.jungle.model.evidence

import me.edens.jungle.model.Model
import me.edens.jungle.model.ModelChange
import me.edens.jungle.model.Place

interface Evidence {
    val source: Place
}

infix fun Model.withEvidence(evidence: Evidence) = ModelChange(this, listOf(evidence))
fun Model.withNoEvidence() = ModelChange(this, emptyList())