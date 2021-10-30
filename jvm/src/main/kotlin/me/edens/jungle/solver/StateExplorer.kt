package me.edens.jungle.solver

import me.edens.jungle.model.*
import me.edens.jungle.model.actions.HumanAction
import java.util.*

const val hardExplorationLimit = 1000 * 1000 // 1 million

class StateExplorer {
    private val pruner = ActionPruner()
    private val stateCache = StateCache()

    private val states by lazy {
        val seed = InitialNode
        val open = ArrayDeque<Node>(listOf(seed))
        val stateLookup = mutableMapOf<Model, SubsequentNode>()

        while (open.any() && stateLookup.size < hardExplorationLimit) {
            val current = open.removeFirst()
            for (action in current.state.actionOptions.filter { pruner.allows(it, current.state) }) {
                val node = SubsequentNode(Link(current, action), stateCache)
                val existingNode = stateLookup[node.state]
                if (existingNode != null) {
//                    if (node.pathLength < existingNode.pathLength) {
//                        existingNode.link = node.link
//                    }
                } else if (node.pathLength < 12) {
                    stateLookup += node.state to node
                    open.addFirst(node)
                }
            }
        }
        ExplorationResult(stateLookup + (seed.state to seed), stateLookup.size >= hardExplorationLimit)
    }

    val reachedHardExplorationLimit by lazy { states.reachedHardLimit }
    val numberExplored by lazy { states.map.keys.size }

    fun findRunsThatSatisfy(filter: (Model) -> Boolean): Sequence<Node> {
        return states.map.values
            .asSequence()
            .filter { filter(it.state) }
            .sortedBy { it.pathLength }
    }
}

data class ExplorationResult(val map: Map<Any, Node>, val reachedHardLimit: Boolean)

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

class SubsequentNode(var link: Link, private val stateCache: StateCache) : Node {
    private val id = stateCache.newId()

    private val modelChange: ModelChange
        get() {
            try {
                return stateCache.get(id) {
                    link.parent.state.update(link.action)
                }
            } catch (e: Exception) {
                throw StateExplorationException(link, link.parent, e)
            }
        }

    override val state: Model
        get() = modelChange.newModel
    override val feedback: List<String>
        get() = modelChange.evidence
            .filter { it.apparentTo(state.human, state.map) }
            .map { it.describe(state.human, state.map) }
    override val pathLength
        get() = 1 + link.parent.pathLength

    override fun toString(): String {
        val action = link.action.description
        val feedback = feedback.joinToString(", ")
        return "$action ($feedback)"
    }
}

data class Link(val parent: Node, val action: HumanAction)

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

class StateExplorationException(link: Link, parent: Node, inner: Throwable) : Exception(
    """An error occured during state exploration. Attempted to apply action '${link.action}' to state:
       ${link.parent.state}
       Preceding path:
       ${parent.fullPathAsString()}""",
    inner
)