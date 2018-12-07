package me.edens.jungle.model.items

import me.edens.jungle.model.Place

class Mushroom(location: Place) : BasicItem("Mushroom", location) {
    override fun atLocation(place: Place) = Mushroom(location = place)
}