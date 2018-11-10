package me.edens.jungle.model.actors

import me.edens.jungle.model.*

class Monster(location: Place) : BasicActor(location) {
    override fun act(): Actor {
        return atLocation(nextPlace(location))
    }

    fun atLocation(place: Place) = Monster(place)

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
}