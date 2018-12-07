package me.edens.jungle.model

import me.edens.jungle.model.actions.DoNothingAction
import me.edens.jungle.model.actions.HumanAction
import me.edens.jungle.model.actions.HumanMoveAction
import me.edens.jungle.model.actors.Actor
import me.edens.jungle.model.actors.MoveableActor
import me.edens.jungle.model.actors.Signature
import kotlin.math.sign

data class Human(override val location: Place) : MoveableActor {
    override val signature = Signature.Human

    fun actions(model: Model): Sequence<HumanAction> {
        return sequenceOf(
                DoNothingAction()
        ) + movementActions(model)
    }

    override fun act(model: Model) = DoNothingAction()

    private fun movementActions(model: Model): Sequence<HumanAction> {
        return model.map
                .getNeighbours(location)
                .asSequence()
                .filter { it.accessibleTo(signature) }
                .map { transition -> HumanMoveAction(this, transition) }
    }

    override fun atLocation(location: Place): Actor {
        return copy(location = location)
    }

}