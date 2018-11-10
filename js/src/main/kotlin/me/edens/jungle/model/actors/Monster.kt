package me.edens.jungle.model.actors

import me.edens.jungle.model.*

data class Monster(override val location: Place, val inhaled: Boolean) : BasicActor() {
    override fun act(model: Model): ActorAction {
        return when {
            inhaled -> breathFire(model)
            model.human.location == MonsterNest -> protectBabies()
            model.human.location == this.location -> inhale()
            else -> atLocation(nextPlace(location))
        }
    }

    private fun atLocation(place: Place) = update("Monster moves to $place") {
        copy(location = place)
    }
    private fun inhale() = update("Monster inhales") {
        copy(inhaled = true)
    }
    private fun breathFire(model: Model) = updateModel("Monster breaths fire on you, killing you") {
        model.copy(status = Status.Death)
    }
    private fun protectBabies() = update("Monster charges to be babies rescue, nostrils flaming") {
        copy(location = MonsterNest, inhaled = true)
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
}