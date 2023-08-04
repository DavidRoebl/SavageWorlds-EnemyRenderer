package dev.roebl.savageworlds.enemyrenderer.model

import org.apache.commons.collections4.map.CaseInsensitiveMap

data class Enemy(
    val name: String,
    val attributes: Attributes,
    val skills: CaseInsensitiveMap<String, Die>,
    val pace: String,
    val parry: String,
    val toughness: String,
    val powers: Array<String>,
    val equipment: Array<String>,
    val isWildcard: Boolean,
    val notes: Array<String>,
    val salvage: Salvage,
    val hindrances: Array<String>,
    val edges: Array<String>
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
        if (!powers.contentEquals(other.powers)) return false
        if (!equipment.contentEquals(other.equipment)) return false
        if (isWildcard != other.isWildcard) return false
        if (!notes.contentEquals(other.notes)) return false
        if (salvage != other.salvage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + attributes.hashCode()
        result = 31 * result + skills.hashCode()
        result = 31 * result + pace.hashCode()
        result = 31 * result + parry.hashCode()
        result = 31 * result + toughness.hashCode()
        result = 31 * result + powers.contentHashCode()
        result = 31 * result + equipment.contentHashCode()
        result = 31 * result + isWildcard.hashCode()
        result = 31 * result + notes.contentHashCode()
        result = 31 * result + salvage.hashCode()
        return result
    }
}

