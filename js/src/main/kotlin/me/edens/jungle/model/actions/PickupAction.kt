package me.edens.jungle.model.actions

import me.edens.jungle.model.Action
import me.edens.jungle.model.Inventory
import me.edens.jungle.model.Model
import me.edens.jungle.model.items.Item

class PickupAction(val item: Item) : Action {
    override val description = "Pickup $item"

    override fun apply(model: Model) = model.updateItem(item) {
        item.atLocation(Inventory)
    }
}