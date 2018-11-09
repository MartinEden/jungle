package me.edens.jungle.model.items

import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Model
import me.edens.jungle.model.Place
import me.edens.jungle.model.actions.PickupAction

interface Item {
    val description: String
    val location: Place

    fun affordances(state: Model): Sequence<Action>
    fun atLocation(place: Place): Item
}

abstract class BasicItem(
        override val description: String,
        override val location: Place
) : Item {
    override fun affordances(state: Model) = moveableAffordances()

    override fun toString() = description

    override fun equals(other: Any?) = if (other != null && other::class == this::class) {
        fieldsAreEqual(other as Item)
    } else {
        false
    }

    protected open fun fieldsAreEqual(other: Item) = location == other.location
}

fun Item.moveableAffordances(): Sequence<Action> {
    val item = this
    return sequence {
        if (item.location != Inventory) {
            yield(PickupAction(item))
        }
    }
}