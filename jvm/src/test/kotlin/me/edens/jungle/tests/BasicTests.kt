package me.edens.jungle.tests

import me.edens.jungle.solver.StateExplorer
import me.edens.jungle.model.Status
import me.edens.jungle.model.Model
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class BasicTests {
    @Test
    fun `there exists some way of losing`() {
        assertThatRunExistsThatSatisfies { it.status == Status.Death }
    }

    @Test
    @Disabled
    fun `there exists some way of winning`() {
        assertThatRunExistsThatSatisfies { it.status == Status.Victory }
    }

    private fun assertThatRunExistsThatSatisfies(filter: (Model) -> Boolean) {
        assertThat(explorer.findRunsThatSatisfy(filter).any())
                .withFailMessage("Unable to find any run that satisfies the given condition")
                .isTrue()
    }

    companion object {
        val explorer = StateExplorer()
    }
}