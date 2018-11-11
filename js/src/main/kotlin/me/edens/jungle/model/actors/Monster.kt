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
        val inhaled: Boolean
) : BasicActor(Signature.Monster) {
    override fun act(model: Model): Action {
        return when {
            inhaled -> breathFire()
            model.human.location == MonsterNest -> protectBabies()
            model.human.location == this.location -> inhale()
            else -> moveTo(nextPlace(location))
        }
    }

    private fun moveTo(place: Place) = MoveAction(this, place)

    private fun inhale() = object : ActorAction<Monster>(this) {
        override fun update(actor: Monster) = actor.copy(inhaled = true).withEvidence {
            TextEvidence(Signature.Monster, "Monster inhales", location)
        }
    }

    private fun breathFire() = object : Action {
        override fun apply(model: Model) = model.copy(status = Status.Death) withEvidence
                TextEvidence(Signature.Monster, "Monster breaths fire on you, killing you", location)
    }

    private fun protectBabies() = object : ActorAction<Monster>(this) {
        override fun update(actor: Monster) = copy(location = MonsterNest, inhaled = true).withEvidence {
            TextEvidence(Signature.Monster, "Monster charges to be babies rescue, nostrils flaming", location)
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
}