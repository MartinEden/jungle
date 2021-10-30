package me.edens.jungle.tests

import me.edens.jungle.model.Inventory
import me.edens.jungle.solver.StateExplorer
import me.edens.jungle.model.Status
import me.edens.jungle.model.Model
import me.edens.jungle.model.PigsPlace
import me.edens.jungle.model.actors.PigCarcass
import me.edens.jungle.model.items.Mushroom
import me.edens.jungle.solver.fullPathAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class BasicTests {
    @Test
    fun `there exists some way of losing`() {
        assertThatARunSatisfies { it.status == Status.Death }
    }

    @Test
    fun `there exists some way of winning`() {
        assertThatARunSatisfies { it.status == Status.Victory }
    }

    @Test
    fun `human cannot enter undergrowth`() {
        assertThatNoRunSatisfies { it.human.location == PigsPlace.Undergrowth }
    }

    @Test
    fun `human can get pig carcass`() {
        assertThatARunSatisfies { state ->
            state.inventory.any { it is PigCarcass }
        }
    }

    private fun assertThatARunSatisfies(filter: (Model) -> Boolean) {
        val run = explorer.findRunsThatSatisfy(filter).firstOrNull()
        assertThat(run)
                .withFailMessage("Unable to find any run that satisfies the given condition")
                .isNotNull
        println(run!!.fullPathAsString())
    }

    private fun assertThatNoRunSatisfies(filter: (Model) -> Boolean) {
        val runs = explorer.findRunsThatSatisfy(filter).toList()
        assertThat(runs.isEmpty())
                .withFailMessage("The following runs existed that satisfied the condition, when I expected " +
                        "none: ${runs.joinToString("\n- ")}")
                .isTrue()
    }

    companion object {
        val explorer = StateExplorer()
    }
}