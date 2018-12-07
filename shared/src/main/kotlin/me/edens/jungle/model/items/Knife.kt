package me.edens.jungle.model.items

import me.edens.jungle.model.*
import me.edens.jungle.model.actions.HumanAction
import me.edens.jungle.model.actors.ICanWound
import me.edens.jungle.model.actors.Monster
import me.edens.jungle.model.actors.Pig
import me.edens.jungle.model.actors.PigCarcass
import me.edens.jungle.model.evidence.VisualEvidence
import me.edens.jungle.model.evidence.withEvidence
import me.edens.jungle.model.evidence.withNoEvidence

class Knife(location: Place) : BasicItem("Knife", location) {
    override fun affordances(state: Model): Sequence<HumanAction> {
        return super.affordances(state) + sequence {
            state.withIfPresent<Parachute> {
                yield(CutParachuteIntoStripsAction(it))
            }
            yieldAll(state.here.filterIsInstance<ICanWound>().map {
                AttackAction(this@Knife, it)
            })
        }
    }

    override fun atLocation(place: Place): Item {
        return Knife(location = place)
    }

    class CutParachuteIntoStripsAction(private val parachute: Parachute) : HumanAction {
        override val description = "Use the knife to cut the parachute into strips"

        override fun apply(model: Model) = model.updateItem(parachute) {
            ParachuteStrips(Inventory)
        }.withNoEvidence()
    }

    class AttackAction(private val knife: Knife, private val target: ICanWound) : HumanAction {
        override val description = "Attack the $target with your knife"

        override fun apply(model: Model) = result(model).andThen { newModel ->
            newModel.removeItem(knife).withEvidence(VisualEvidence("Your knife breaks", knife.location))
        }

        private fun result(model: Model): ModelChange {
            return when (target) {
                is Monster -> model.copy(status = Status.Death).withEvidence(VisualEvidence("""
                            |You throw yourself at the beast's neck, attacking with ferocity, but your knife is unable to
                            |do more than score its thick leathery flesh. Before you can dodge aside, it whirls and gores
                            |you fatally.""",
                        model.human.location
                ))
                is Pig -> model.removeActor(target).addItem(PigCarcass(target.location)).withEvidence(VisualEvidence(
                        "You grab the pig around the neck and as it bucks and squeals you quickly slit its throat.",
                        model.human.location
                ))
                else -> throw Exception("Need to handle all ICanWound entities here")
            }
        }
    }
}