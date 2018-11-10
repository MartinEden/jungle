package me.edens.jungle.model

import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.items.Item

data class Model(
        val status: Status,
        val map: Map,
        val human: Human,
        val items: List<Item>,
        val actors: List<Actor>
) {
    val actions: Sequence<Action> by lazy {
        when (status) {
            Status.InProgress -> human.actions(this) + here.flatMap { it.affordances(this).toList() }
            Status.Victory -> emptySequence()
        }
    }

    fun update(action: Action): Model {
        val afterAction = action.apply(this)
        return actors.fold(afterAction) { state, actor ->
            state.updateActor(actor) { it.act() }
        }
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

    fun <T : Item, R : Item> updateItem(item: T, modified: (T) -> R): Model {
        return copy(items = items - listOf(item) + modified(item))
    }
    fun <T : Actor, R : Actor> updateActor(actor: T, modified: (T) -> R): Model {
        return copy(actors = actors - listOf(actor) + modified(actor))
    }
}