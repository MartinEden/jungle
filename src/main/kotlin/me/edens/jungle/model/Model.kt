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
                items
                        .filter { it.location == human.location }
                        .flatMap { it.affordances(state).toList() }
    }

    fun <T: Item> updateItem(item: T, modified: (T) -> T): Model {
        return copy(items = items - listOf(item) + modified(item))
    }

    companion object {
        val initial by lazy {
            val map = Map.initial
            Model(map, Human(Clearing), initialItems())
        }
    }
}