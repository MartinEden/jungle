package me.edens.jungle.solver

import me.edens.jungle.model.Inventory
import me.edens.jungle.model.items.Mushroom

fun main(args: Array<String>) {
    val explorer = StateExplorer()
    val runs = explorer.findRunsThatSatisfy {
        it.itemsAt<Mushroom>(Inventory).any()
    }.toList()
    println("Total number of solutions: ${runs.size}")
    if (runs.any()) {
        println("Shortest solution:")
        println(runs.first().allSteps().joinToString("\n→ "))
    } else {
        println("Unable to find run that satisfies solution")
    }
}