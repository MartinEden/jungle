package me.edens.jungle.model.items

import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place
import me.edens.jungle.model.actions.PickupAction

interface Item {
    val location: Place

    fun affordances(state: Model): Sequence<Action>
    fun atLocation(place: Place): Item
}

fun Item.moveableAffordances(state: Model): Sequence<Action> {
    val item = this
    return sequence {
        if (item.location != Inventory) {
            yield(PickupAction(item))
        }
    }
}