package me.edens.jungle.model.evidence

import me.edens.jungle.model.Human

data class DualEvidence(val visual: VisualEvidence, val audio: AudioEvidence): Evidence {
    override fun apparentTo(observer: Human) = visual.apparentTo(observer) || audio.apparentTo(observer)
    override fun describe(observer: Human) = if (visual.apparentTo(observer)) {
        visual.describe(observer)
    } else {
        audio.describe(observer)
    }
}