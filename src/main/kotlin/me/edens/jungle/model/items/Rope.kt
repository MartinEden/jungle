package me.edens.jungle.model.items

import me.edens.jungle.model.Place

class Rope(override val location: Place)
    : BasicItem("A rough rope made of strips of parachute cloth", location) {
    override fun atLocation(place: Place) = Rope(location = location)
}