package me.edens.jungle.tests

import me.edens.jungle.model.Inventory
import me.edens.jungle.solver.StateExplorer
import me.edens.jungle.model.Status
import me.edens.jungle.model.Model
import me.edens.jungle.model.PigsPlace
import me.edens.jungle.model.actors.PigCarcass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class BasicTests {
    @Test
    fun `there exists some way of losing`() {
        assertThatARunSatisfies { it.status == Status.Death }
    }

    @Test
    @Disabled
    fun `there exists some way of winning`() {
        assertThatARunSatisfies { it.status == Status.Victory }
    }

    @Test
    fun `human cannot enter undergrowth`() {
        assertThatNoRunSatisfies { it.human.location == PigsPlace.Undergrowth }
    }

    @Test
    fun `human can get pig carcass`() {
        assertThatARunSatisfies { state -> state.items.any { it is PigCarcass && it.location == Inventory } }
    }

    private fun assertThatARunSatisfies(filter: (Model) -> Boolean) {
        assertThat(explorer.findRunsThatSatisfy(filter).any())
                .withFailMessage("Unable to find any run that satisfies the given condition")
                .isTrue()
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