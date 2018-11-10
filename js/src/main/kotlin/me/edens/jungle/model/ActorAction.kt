package me.edens.jungle.model

import me.edens.jungle.model.actors.Actor

interface ActorAction {
    fun apply(change: ModelChange): ModelChange
}

class IsolatedActorAction<TActor: Actor>(
        private val actor: TActor,
        private val feedback: String,
        private val func: (TActor) -> TActor
): ActorAction {
    override fun apply(change: ModelChange) = ModelChange(
            change.newModel.updateActor(actor, func),
            change.feedback + feedback
    )
}

class GlobalActorAction(
        private val feedback: String,
        private val func: (Model) -> Model
): ActorAction {
    override fun apply(change: ModelChange) = ModelChange(
            func(change.newModel),
            change.feedback + feedback
    )
}

fun <TActor: Actor> TActor.update(
        feedback: String,
        func: (TActor) -> TActor
) = IsolatedActorAction(this, feedback, func)

fun Actor.updateModel(
        feedback: String,
        func: (Model) -> Model
) = GlobalActorAction(feedback, func)

