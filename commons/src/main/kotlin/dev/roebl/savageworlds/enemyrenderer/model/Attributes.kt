package dev.roebl.savageworlds.enemyrenderer.model

data class Attributes(
    val agility: Die,
    val smarts: Die,
    val spirit: Die,
    val strength: Die,
    val vigor: Die,
) {
    fun asMap(): Map<String, Die> = sortedMapOf(
        "agility" to agility,
        "smarts" to smarts,
        "spirit" to spirit,
        "strength" to strength,
        "vigor" to vigor
    )
}