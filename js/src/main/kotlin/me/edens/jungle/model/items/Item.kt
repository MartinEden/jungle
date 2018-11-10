package me.edens.jungle.model.items

import me.edens.jungle.model.*
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
) : StructuralEqualityBase(), Item {
    override fun affordances(state: Model) = moveableAffordances()

    override fun toString() = description

    override fun fieldsAreEqual(other: Any): Boolean {
        val x = other as BasicItem
        return description == x.description && location == x.location
    }
}

fun Item.moveableAffordances(): Sequence<Action> {
    val item = this
    return sequence {
        if (item.location != Inventory) {
            yield(PickupAction(item))
        }
    }
}