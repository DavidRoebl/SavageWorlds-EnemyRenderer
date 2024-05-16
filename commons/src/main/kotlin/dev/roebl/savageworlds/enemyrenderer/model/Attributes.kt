package dev.roebl.savageworlds.enemyrenderer.model

data class Attributes(
    val agility: ModifiedDie,
    val smarts: ModifiedDie,
    val spirit: ModifiedDie,
    val strength: ModifiedDie,
    val vigor: ModifiedDie,
) {
    fun asMap(): Map<String, ModifiedDie> = sortedMapOf(
        "agility" to agility,
        "smarts" to smarts,
        "spirit" to spirit,
        "strength" to strength,
        "vigor" to vigor
    )
}