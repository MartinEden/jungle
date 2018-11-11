package me.edens.jungle.model.actors

import me.edens.jungle.model.*
import me.edens.jungle.model.actions.ActorAction
import me.edens.jungle.model.actions.MoveAction
import me.edens.jungle.model.actions.withEvidence
import me.edens.jungle.model.evidence.Evidence
import me.edens.jungle.model.evidence.TextEvidence
import me.edens.jungle.model.evidence.withEvidence

data class Monster(
        override val location: Place,
        val inhaled: Boolean,
        val target: Place? = null
) : BasicActor(Signature.Monster), MoveableActor {
    override fun act(model: Model): Action {
        val sight = getSightOf(model.human, model)
        return when {
            inhaled -> breathFire()
            model.human.location == MonsterNest -> protectBabies()
            sight is Sight.Saw -> inhale(sight.place)
            else -> moveTo(nextPlace(location))
        }
    }

    private fun moveTo(place: Place) = MoveAction(this, place)

    private fun inhale(target: Place) = object : ActorAction<Monster>(this) {
        override fun update(actor: Monster) = actor.copy(inhaled = true, target = target).withEvidence {
            TextEvidence("Monster inhales", location)
        }
    }

    private fun breathFire() = BreathFireAction(this, target!!)

    private fun protectBabies() = object : ActorAction<Monster>(this) {
        override fun update(actor: Monster) = copy(location = MonsterNest, inhaled = true).withEvidence {
            TextEvidence("Monster charges to be babies rescue, nostrils flaming", location)
        }
    }

    private fun nextPlace(place: Place) = when (place) {
        MonsterNest -> HivePlace
        HivePlace -> SpiderLair
        SpiderLair -> Cliff
        Cliff -> Clearing
        Clearing -> CrashSite
        CrashSite -> VinePlace
        VinePlace -> FlowerGrove
        FlowerGrove -> PigsPlace
        PigsPlace -> HivePlace
        else -> throw Exception("Monster unable to determine next place to move to. Currently at $place")
    }

    override fun fieldsAreEqual(other: Any): Boolean {
        return super.fieldsAreEqual(other)
                && inhaled == (other as Monster).inhaled
    }

    override fun atLocation(location: Place) = copy(location = location)

    class BreathFireAction(private val monster: Monster, private val target: Place): Action {
        override fun apply(model: Model): ModelChange {
            val newModel = model.replaceActor(monster, monster.copy(inhaled = false, target = null))
            return if (newModel.human.location == target) {
                newModel.copy(status = Status.Death) withEvidence TextEvidence("The monster breaths fire on you, killing you", target)
            } else {
                newModel withEvidence TextEvidence("You hear the monster's flames whoosh in the jungle behind you", target)
            }
        }
    }
}