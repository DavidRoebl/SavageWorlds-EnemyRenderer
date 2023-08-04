package dev.roebl.savageworlds.enemyrenderer.model

import org.apache.commons.collections4.map.CaseInsensitiveMap

data class Enemy(
    val name: String,
    val attributes: Attributes,
    val skills: CaseInsensitiveMap<String, Die>,
    val pace: String,
    val parry: String,
    val toughness: String,
    val powers: Map<String, String>,
    val equipment: Map<String, String>,
    val isWildcard: Boolean,
    val notes: Map<String, String>,
    val salvage: Salvage,
    val hindrances: Map<String, String>,
    val edges: Map<String, String>
) {

    val sortedSkills: Map<String, Die>
        get() = skills.toSortedMap(baseSkillsFirst andThen byNaturalOrder)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Enemy

        if (name != other.name) return false
        if (attributes != other.attributes) return false
        if (skills != other.skills) return false
        if (pace != other.pace) return false
        if (parry != other.parry) return false
        if (toughness != other.toughness) return false
        if (powers != other.powers) return false
        if (equipment != other.equipment) return false
        if (isWildcard != other.isWildcard) return false
        if (notes != other.notes) return false
        if (salvage != other.salvage) return false
        if (hindrances != other.hindrances) return false
        if (edges != other.edges) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + attributes.hashCode()
        result = 31 * result + skills.hashCode()
        result = 31 * result + pace.hashCode()
        result = 31 * result + parry.hashCode()
        result = 31 * result + toughness.hashCode()
        result = 31 * result + powers.hashCode()
        result = 31 * result + equipment.hashCode()
        result = 31 * result + isWildcard.hashCode()
        result = 31 * result + notes.hashCode()
        result = 31 * result + salvage.hashCode()
        result = 31 * result + hindrances.hashCode()
        result = 31 * result + edges.hashCode()
        return result
    }


}

