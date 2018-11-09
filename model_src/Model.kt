package me.edens.jungle.model

data class Model(
        val map: Map,
        val human: Human
) {
    companion object {
        val initial by lazy {
            val map = Map.initial
            Model(map, Human(map.getPlace("crash-site")))
        }
    }
}