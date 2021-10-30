package me.edens.jungle.model.items

import me.edens.jungle.model.*
import me.edens.jungle.model.actions.DropAction
import me.edens.jungle.model.actions.HumanAction
import me.edens.jungle.model.actions.PickupAction

interface Item : Thing {
    val description: String

    fun affordances(state: Model): Sequence<HumanAction>
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

fun Item.moveableAffordances(): Sequence<HumanAction> {
    val item = this
    return sequence {
        if (item.location == Inventory) {
            yield(DropAction(item))
        } else {
            yield(PickupAction(item))
        }
    }
}