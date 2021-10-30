package me.edens.jungle.solver

import me.edens.jungle.model.*

class StateCache(private val maxSize: Int = 2500) {
    private val map: MutableMap<Int, ModelChange> = mutableMapOf()
    private val tracker = mutableListOf<Int>()
    private var full = false
    private var counter = 0

    fun get(key: Int, creator: () -> ModelChange): ModelChange {
        val model = map[key]
        return if (model != null) {
            model
        } else {
            val newModel = creator()
            map[key] = newModel
            tracker.add(key)
            clearOldestEntryIfNeeded()
            newModel
        }
    }

    private fun clearOldestEntryIfNeeded() {
        if (full || tracker.size > maxSize) {
            full = true
            val key = tracker.removeAt(0)
            map.remove(key)
        }
    }

    fun newId(): Int {
        counter++
        return counter
    }
}