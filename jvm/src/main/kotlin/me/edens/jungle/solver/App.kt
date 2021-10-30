package me.edens.jungle.solver

import me.edens.jungle.model.*
import me.edens.jungle.model.actors.PigCarcass

fun main(args: Array<String>) {
    val explorer = StateExplorer()
    if (explorer.reachedHardExplorationLimit) {
        handleHardLimit(explorer)
    } else {
        println("Explored ${explorer.numberExplored} states")
        val runs = explorer.findRunsThatSatisfy {
            it.inventory.any { it is PigCarcass } && it.status == Status.InProgress
        }.toList()
        println("Total number of solutions: ${runs.size}")
        if (runs.any()) {
            println("Shortest solution:")
            println(runs.first().fullPathAsString())
        } else {
            println("Unable to find run that satisfies solution")
        }
    }
}

private fun handleHardLimit(explorer: StateExplorer) {
    println("Unable to explore all states - reached hard exploration limit.")
    println("Here are the last runs I explored:")
    val runs = explorer.findRunsThatSatisfy { true }.toList()
    for (run in runs.takeLast(10)) {
        println("\n" + run.fullPathAsString())
    }
    println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1")
}

fun Node.fullPathAsString() = allSteps().joinToString("\n→ ")