package me.edens.jungle.model

import ModelStateException
import me.edens.jungle.model.actions.HumanAction
import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.actors.actorOrder
import me.edens.jungle.model.items.Item

data class Model(
        val status: Status,
        val map: Map,
        val items: List<Item>,
        val actors: List<Actor>
) {
    init {
        if (actors.filterIsInstance<Human>().count() > 1) {
            throw ModelStateException("More than one human in model")
        }
    }

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
        return actorOrder.fold(afterAction) { change, signature ->
            val actors = change.newModel.actors.filter { it.signature == signature }
            updateActors(actors, change)
        }
    }

    private fun updateActors(actors: List<Actor>, initialChange: ModelChange): ModelChange {
        return actors.fold(initialChange) { change, actor ->
            if (change.newModel.status == Status.InProgress) {
                change.andThen { model -> actor.act(model).apply(model) }
            } else {
                change
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

    val inventory by lazy {
        items.filter { it.location == Inventory }
    }

    fun at(place: Place): List<Thing> {
        return things.filter { it.location == place }
    }

    inline fun <reified T : Actor> actorsAt(place: Place): List<T> {
        return actors.filter { it.location == place }.filterIsInstance<T>()
    }

    inline fun <reified T : Item> itemsAt(place: Place): List<T> {
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

    fun removeActor(actor: Actor): Model = if (actor in actors) {
        copy(actors = actors - actor)
    } else {
        throw ModelStateException("Attempted to remove $actor from model when it didn't exist.\nState: $this")
    }
    fun addActor(actor: Actor): Model = copy(actors = actors + actor)
    fun replaceActor(old: Actor, new: Actor) = removeActor(old).addActor(new)
}