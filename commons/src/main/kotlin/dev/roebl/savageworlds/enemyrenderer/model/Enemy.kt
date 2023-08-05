package dev.roebl.savageworlds.enemyrenderer.model

import org.apache.commons.collections4.map.CaseInsensitiveMap

data class Enemy(
    val name: String,
    val isWildcard: Boolean,
    val attributes: Attributes,
    val skills: CaseInsensitiveMap<String, Die>,
    val pace: String,
    val parry: String,
    val toughness: String,
    val gear: Map<String, String>,
    val salvage: Salvage,
    val specialAbilities: Map<String, String>
) {
    val sortedSkills: Map<String, Die>
        get() = skills.toSortedMap(baseSkillsFirst andThen byNaturalOrder)
}

