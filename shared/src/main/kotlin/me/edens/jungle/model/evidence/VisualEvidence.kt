package me.edens.jungle.model.evidence

import me.edens.jungle.model.Human
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Map
import me.edens.jungle.model.Place

data class VisualEvidence(
        private val description: String,
        private val source: Place
): Evidence {
    override fun apparentTo(observer: Human, map: Map) = observer.location == source || source == Inventory
    override fun describe(observer: Human, map: Map) = description.trimMargin().replace("\n", " ")
}