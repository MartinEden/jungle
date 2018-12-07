package me.edens.jungle.model.evidence

import me.edens.jungle.model.Human
import me.edens.jungle.model.Map

data class EvidenceGroup(val evidence: List<Evidence>) : Evidence {
    override fun apparentTo(observer: Human, map: Map)
            = evidence.any { it.apparentTo(observer, map) }
    override fun describe(observer: Human, map: Map)
            = evidence.first { it.apparentTo(observer, map) }.describe(observer, map)
}