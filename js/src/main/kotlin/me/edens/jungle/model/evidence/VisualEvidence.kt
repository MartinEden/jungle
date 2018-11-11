package me.edens.jungle.model.evidence

import me.edens.jungle.model.Human
import me.edens.jungle.model.Place

data class VisualEvidence(
        private val description: String,
        private val source: Place
): Evidence {
    override fun apparentTo(observer: Human) = observer.location == source
    override fun describe(observer: Human) = description
}