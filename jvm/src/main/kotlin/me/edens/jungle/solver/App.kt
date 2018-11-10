package me.edens.jungle.solver

import me.edens.jungle.model.Status

fun main(args: Array<String>) {
    val explorer = StateExplorer()
    val runs = explorer.findRunsThatSatisfy(false) {
        it.status == Status.Death
    }.toList()
    println("Total number of solutions: ${runs.size}")
    if (runs.any()) {
        println("Shortest solution:")
        println(runs.first().allSteps().joinToString("\n→ "))
    } else {
        println("Unable to find run that satisfies solution")
    }
}