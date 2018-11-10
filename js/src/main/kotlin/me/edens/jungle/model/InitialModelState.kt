package me.edens.jungle.model

import me.edens.jungle.model.actors.Monster
import me.edens.jungle.model.items.Knife
import me.edens.jungle.model.items.Parachute

object InitialModelState {
    val model by lazy {
        val map = Map.initial
        Model(Status.InProgress, map, Human(Clearing), initialItems, initialActors)
    }

    private val initialItems by lazy {
        listOf(
                Knife(Inventory),
                Parachute(CrashSite)
        )
    }

    private val initialActors by lazy {
        listOf(
                Monster(MonsterNest, inhaled = false)
        )
    }
}