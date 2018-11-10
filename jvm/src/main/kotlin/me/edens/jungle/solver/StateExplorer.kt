package me.edens.jungle.solver

import me.edens.jungle.model.Action
import me.edens.jungle.model.InitialModelState
import me.edens.jungle.model.Model
import java.util.*

const val hardExplorationLimit = 10000

class StateExplorer {
    private val states by lazy {
        val seed = InitialNode
        val open = ArrayDeque<Node>(listOf(seed))
        val stateLookup = mutableMapOf<Model, SubsequentNode>()

        while (open.any() && stateLookup.size < hardExplorationLimit) {
            val current = open.removeFirst()
            for (action in current.state.actions) {
                val node = SubsequentNode(Link(current, action))
                val existingNode = stateLookup[node.state]
                if (existingNode != null) {
                    if (node.pathLength < existingNode.pathLength) {
                        existingNode.link = node.link
                    }
                } else {
                    stateLookup += node.state to node
                    open.addLast(node)
                }
            }
        }
        stateLookup + (seed.state to seed)
    }

    fun findRunsThatSatisfy(showBest: Boolean, filter: (Model) -> Boolean): Sequence<Node> {
        val subset = states.values
                .asSequence()
                .filter { filter(it.state) }
                .sortedBy { it.pathLength }
        return if (showBest && subset.any()) {
            sequenceOf(subset.first())
        } else {
            subset
        }
    }
}

interface Node {
    val state: Model
    val feedback: List<String>
    val pathLength: Int
}

object InitialNode : Node {
    override val state = InitialModelState.model
    override val feedback = emptyList<String>()

    override val pathLength = 0

    override fun toString() = "∅"
}

class SubsequentNode(var link: Link) : Node {
    private val modelChange by lazy {
        link.parent.state.update(link.action)
    }

    override val state: Model by lazy { modelChange.newModel }
    override val feedback: List<String> by lazy { modelChange.feedback }
    override val pathLength = 1 + link.parent.pathLength

    override fun toString(): String {
        val action = link.action.description
        val feedback = feedback.joinToString(", ")
        return "$action ($feedback)"
    }
}

data class Link(val parent: Node, val action: Action)

fun Node.allSteps(): List<Node> {
    val that = this
    return sequence {
        var node = that
        yield(node)
        while (node is SubsequentNode) {
            node = node.link.parent
            yield(node)
        }
    }.toList().reversed()
}