package me.edens.jungle.model

abstract class Place(val id: String) {
    override fun toString() = id
}

object Inventory : Place("inventory")

object WolfDen : Place("wolf-den")
object MonsterNest : Place("monster-nest")
object PigsPlace : Place("pig-place")
object HivePlace : Place("hive-place")
object SpiderLair : Place("spider-lair")
object FlowerGrove : Place("flower-grove")
object Clearing : Place("clearing")
object Cliff : Place("cliff")
object VinePlace : Place("vine-place")
object CrashSite : Place("crash-site")
object Cave : Place("cave")
object CliffLedge : Place("cliff-ledge")


class PlaceTransitions(val source: Place, transitions: Set<Transition>) {
    val transitions = transitions.associateBy { it.target }
}

data class Transition(val target: Place, val description: String)

infix fun String.transitionTo(place: Place) = Transition(place, this)