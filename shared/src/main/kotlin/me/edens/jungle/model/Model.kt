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
            Status.InProgress -> human.actions(this) + here
                    .filterIsInstance<Item>()
                    .flatMap { it.affordances(this).toList() }
            else -> emptySequence()
        }
    }

    fun update(action: Action): ModelChange {
        val afterAction = action.apply(this)
        return actors.fold(afterAction) { cumulativeChange, actor ->
            if (cumulativeChange.newModel.status == Status.InProgress) {
                cumulativeChange.andThen { model -> actor.act(model).apply(model) }
            } else {
                cumulativeChange
            }
        }
    }

    val human by lazy {
        actors.filterIsInstance<Human>().single()
    }

    private val things by lazy { actors + items }

    val here by lazy {
        things.filter { it.location == human.location || it.location == Inventory }
    }

    fun at(place: Place): List<Thing> {
        return things.filter { it.location == place }
    }

    inline fun <reified T : Actor> actorsAt(place: Place): List<T> {
        return actors.filter { it.location == place }.filterIsInstance<T>()
    }

    inline fun <reified T: Item> itemsAt(place: Place): List<T> {
        return items.filter { it.location == place }.filterIsInstance<T>()
    }

    inline fun <reified T> withIfPresent(action: (T) -> Unit) {
        val item = here.filterIsInstance<T>().singleOrNull()
        if (item != null) {
            action(item)
        }
    }

    fun <T : Item, R : Item> updateItem(item: T, modified: (T) -> R): Model {
        return replaceItem(item, modified(item))
    }

    fun removeItem(item: Item): Model = copy(items = items - item)
    fun addItem(item: Item): Model = copy(items = items + item)
    fun replaceItem(old: Item, new: Item) = removeItem(old).addItem(new)

    fun removeActor(actor: Actor): Model = copy(actors = actors - actor)
    fun addActor(actor: Actor): Model = copy(actors = actors + actor)
    fun replaceActor(old: Actor, new: Actor) = removeActor(old).addActor(new)
}