package me.edens.jungle.model.actions

import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Model
import me.edens.jungle.model.evidence.withNoEvidence
import me.edens.jungle.model.items.Item

class PickupAction(private val item: Item) : HumanAction {
    override val description = "Pickup $item"

    override fun apply(model: Model) = model.updateItem(item) {
        item.atLocation(Inventory)
    }.withNoEvidence()
}

class DropAction(private val item: Item): HumanAction {
    override val description = "Drop $item"

    override fun apply(model: Model) = model.updateItem(item) {
        item.atLocation(model.human.location)
    }.withNoEvidence()
}