package me.edens.jungle.model

import me.edens.jungle.model.actors.FireBreath
import me.edens.jungle.model.actors.Monster
import me.edens.jungle.model.items.Knife
import me.edens.jungle.model.items.Parachute

object InitialModelState {
    val model by lazy {
        val map = Map.initial
        Model(Status.InProgress, map, initialItems, initialActors)
    }

    private val initialItems by lazy {
        listOf(
                Knife(Inventory),
                Parachute(CrashSite)
        )
    }

    private val initialActors by lazy {
        listOf(
                Human(Clearing),
                Monster(MonsterNest, breath = FireBreath.NotReady)
        )
    }
}