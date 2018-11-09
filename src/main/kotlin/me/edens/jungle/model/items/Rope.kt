package me.edens.jungle.model.items

import me.edens.jungle.model.*

class Rope(override val location: Place)
    : BasicItem("A rough rope made of strips of parachute cloth", location) {

    override fun affordances(state: Model): Sequence<Action> {
        return super.affordances(state) + sequence {
            if (state.human.location == MonsterNest) {
                yield(ClimbDownRopeAction())
            }
        }
    }

    override fun atLocation(place: Place) = Rope(location = location)

    class ClimbDownRopeAction : Action {
        override val description = "Use the rope to climb down the from the monster nest and escape into the wider jungle"

        override fun apply(model: Model) = model.copy(status = Status.Victory)
    }
}