package me.edens.jungle.model

import me.edens.jungle.model.actions.HumanAction
import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.items.Item

data class Model(
        val status: Status,
        val map: Map,
        val items: List<Item>,
        val actors: List<Actor>
) {
    val actionOptions: Sequence<HumanAction> by lazy {
        when (status) {
            Status.InProgress -> human.actions(this) + here.flatMap { it.affordances(this).toList() }
            else -> emptySequence()
        }
    }

    fun update(action: Action): ModelChange {
        val afterAction = action.apply(this)
        return actors.fold(afterAction) { state, actor ->
            val change = actor.act(state.newModel).apply(state.newModel)
            ModelChange(change.newModel, state.evidence + change.evidence)
        }
    }

    val human by lazy {
        actors.filterIsInstance<Human>().single()
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
    fun <T : Actor, R : Actor> replaceActor(old: T, new: R): Model {
        return copy(actors = actors - old + new)
    }
}