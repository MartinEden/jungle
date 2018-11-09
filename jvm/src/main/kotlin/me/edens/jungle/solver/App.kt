package me.edens.jungle.solver

import me.edens.jungle.model.Status

fun main(args: Array<String>) {
    val explorer = StateExplorer()
    val runs = explorer.findRunsThatSatisfy(false) {
        it.status == Status.Victory
    }.toList()
    println("Total number of solutions: ${runs.size}")
    println("Shortest solution:")
    println(runs.first().allSteps().toList().reversed().joinToString("\n⟶ "))
}