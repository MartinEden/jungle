package me.edens.jungle.model.evidence

import me.edens.jungle.model.Human
import me.edens.jungle.model.Map
import me.edens.jungle.model.Place

data class AudioEvidence(private val description: String, val source: Place) : Evidence {
    override fun apparentTo(observer: Human, map: Map) = source == observer.location || map.areNeighbours(source, observer.location)
    override fun describe(observer: Human, map: Map): String
    {
        return if (source != observer.location) {
            val direction = map.directionTo(from = observer.location, to = source)
            "From the $direction, you hear $description"
        } else {
            "You hear $description"
        }
    }
}