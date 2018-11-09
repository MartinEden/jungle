package me.edens.jungle.model

import me.edens.jungle.model.items.Item
import me.edens.jungle.model.items.initialItems

data class Model(
        val map: Map,
        val human: Human,
        val items: List<Item>
) {
    fun actions(state: Model): Sequence<Action> {
        return human.actions(state) +
                here.flatMap { it.affordances(state).toList() }
    }

    val here by lazy {
        items.filter { it.location == human.location || it.location == Inventory }
    }

    inline fun <reified T> withIfPresent(action: (T) -> Unit) {
        val item = here.filterIsInstance<T>().singleOrNull()
        if (item != null) {
            action(item)
        }
    }

    fun <T : Item, R: Item> updateItem(item: T, modified: (T) -> R): Model {
        return copy(items = items - listOf(item) + modified(item))
    }

    companion object {
        val initial by lazy {
            val map = Map.initial
            Model(map, Human(Clearing), initialItems())
        }
    }
}