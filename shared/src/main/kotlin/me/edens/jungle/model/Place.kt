package me.edens.jungle.model

abstract class Place(val description: String) {
    override fun toString() = description
}

object Inventory : Place("Inventory")

object WolfDen : Place("wolf's den")
object MonsterNest : Place("monster's nest")
object PigsPlace : Place("pig's place") {
    object Undergrowth : Place("undergrowth")
}
object HivePlace : Place("hive place")
object SpiderLair : Place("spider's lair")
object FlowerGrove : Place("flower grove")
object Clearing : Place("clearing")
object Cliff : Place("cliff")
object VinePlace : Place("viney place")
object CrashSite : Place("crash site")
object Cave : Place("cave")
object CliffLedge : Place("cliff ledge")
