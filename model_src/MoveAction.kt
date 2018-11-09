package me.edens.jungle.model

class MoveAction(val transition: Transition) : Action {
    override val description = "Go ${transition.description} to ${transition.target}"

    override fun apply(model: Model)
            = model.run { copy(human = human.atLocation(transition.target)) }
}