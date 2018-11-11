package me.edens.jungle.model.evidence

import me.edens.jungle.model.Human

data class AudioEvidence(private val description: String) : Evidence {
    override fun apparentTo(observer: Human) = true
    override fun describe(observer: Human) = "You hear $description"
}