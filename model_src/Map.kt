package me.edens.jungle.model

data class Map(val places: Set<Place>, val transitions: Set<PlaceTransitions>) {
    private val transitionMap = transitions.associateBy({ it.source }, { it.transitions })

    fun getPlace(id: String) = places.single { it.id == id }
    fun getNeighbours(place: Place) = transitionMap[place]!!.map { it.value }

    companion object {
        val initial = Map(
                setOf(
                        WolfDen,
                        MonsterNest,
                        PigsPlace,
                        HivePlace,
                        SpiderLair,
                        FlowerGrove,
                        Clearing,
                        Cliff,
                        VinePlace,
                        CrashSite,
                        Cave,
                        CliffLedge
                ),
                setOf(
                        PlaceTransitions(WolfDen, setOf(
                                "south" transitionTo  PigsPlace
                        )),
                        PlaceTransitions(MonsterNest, setOf(
                                "south" transitionTo HivePlace
                        )),
                        PlaceTransitions(PigsPlace, setOf(
                                "north" transitionTo WolfDen,
                                "east" transitionTo HivePlace,
                                "south" transitionTo FlowerGrove
                        )),
                        PlaceTransitions(HivePlace, setOf(
                                "north" transitionTo MonsterNest,
                                "east" transitionTo SpiderLair,
                                "south" transitionTo Clearing,
                                "west" transitionTo PigsPlace
                        )),
                        PlaceTransitions(SpiderLair, setOf(
                                "south" transitionTo Cliff,
                                "west" transitionTo HivePlace
                        )),
                        PlaceTransitions(FlowerGrove, setOf(
                                "north" transitionTo PigsPlace,
                                "east" transitionTo Clearing,
                                "south" transitionTo VinePlace
                        )),
                        PlaceTransitions(Clearing, setOf(
                                "north" transitionTo HivePlace,
                                "east" transitionTo Cliff,
                                "south" transitionTo CrashSite,
                                "west" transitionTo FlowerGrove
                        )),
                        PlaceTransitions(Cliff, setOf(
                                "north" transitionTo SpiderLair,
                                "west" transitionTo Clearing,
                                "south" transitionTo CliffLedge
                        )),
                        PlaceTransitions(VinePlace, setOf(
                                "north" transitionTo FlowerGrove,
                                "east" transitionTo CrashSite
                        )),
                        PlaceTransitions(CrashSite, setOf(
                                "north" transitionTo Clearing,
                                "east" transitionTo Cave,
                                "west" transitionTo VinePlace
                        )),
                        PlaceTransitions(Cave, setOf(
                                "west" transitionTo CrashSite,
                                "east" transitionTo CliffLedge
                        )),
                        PlaceTransitions(CliffLedge, setOf(
                                "west" transitionTo Cave,
                                "north" transitionTo Cliff
                        ))
                )
        )
    }
}