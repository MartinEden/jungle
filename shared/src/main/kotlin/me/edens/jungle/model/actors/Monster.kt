package me.edens.jungle.model.actors

import me.edens.jungle.model.*
import me.edens.jungle.model.actions.ActorAction
import me.edens.jungle.model.actions.NoisyMoveAction
import me.edens.jungle.model.actions.monster.BreathFireAction
import me.edens.jungle.model.actions.monster.EatCarcassAction
import me.edens.jungle.model.actions.withEvidence
import me.edens.jungle.model.evidence.AudioEvidence
import me.edens.jungle.model.evidence.EvidenceGroup
import me.edens.jungle.model.evidence.VisualEvidence
import me.edens.jungle.model.evidence.withEvidence

data class Monster(
        override val location: Place,
        val breath: FireBreath,
        val hungry: Boolean
) : BasicActor(Signature.Monster), MoveableActor, ICanWound {
    override fun act(model: Model): Action {
        val sight = getSightOf(model.human, model)
        val carcass = model.itemsAt<PigCarcass>(location).firstOrNull()
        return when {
            model.human.location == MonsterNest -> protectBabies()
            breath is FireBreath.Inhaled -> breathFire(sight, breath.targetLastSeenAt)
            sight is Sight.Saw -> inhale(sight.place)
            carcass != null -> EatCarcassAction(this, carcass)
            else -> moveTo(nextPlace(location))
        }
    }

    private fun moveTo(place: Place) = NoisyMoveAction(this, place, "something big crashing through the undergrowth")

    private fun inhale(target: Place) = object : ActorAction<Monster>(this) {
        override fun update(actor: Monster) = actor.copy(breath = FireBreath.Inhaled(target)).withEvidence {
            EvidenceGroup(listOf(
                    VisualEvidence("Monster inhales", location),
                    AudioEvidence("a deep rushing sound, like air being sucked into a bellows", location)
            ))
        }
    }

    private fun breathFire(sight: Sight, previousLocation: Place): Action {
        val newTarget = if (sight is Sight.Saw) {
            sight.place
        } else {
            previousLocation
        }
        return BreathFireAction(this, newTarget)
    }

    private fun protectBabies() = object : ActorAction<Monster>(this) {
        override fun update(actor: Monster) = copy(location = MonsterNest, breath = FireBreath.Inhaled(MonsterNest)).withEvidence {
            VisualEvidence("Monster charges to the babies rescue, nostrils flaming", MonsterNest)
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
                && other is Monster
                && breath == other.breath
                && hungry == other.hungry
    }

    override fun atLocation(location: Place) = copy(location = location)
}

sealed class FireBreath {
    object NotReady : FireBreath() {
        override fun toString() = "Normal"
    }

    data class Inhaled(val targetLastSeenAt: Place) : FireBreath() {
        override fun toString() = "Inhaled"
    }
}