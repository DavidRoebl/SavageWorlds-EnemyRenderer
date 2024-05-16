package dev.roebl.savageworlds.enemyrenderer.model

import org.apache.commons.collections4.map.CaseInsensitiveMap

data class Enemy(
    val name: String,
    val isWildcard: Boolean,
    val attributes: Attributes,
    val skills: CaseInsensitiveMap<String, ModifiedDie>,
    val pace: String,
    val parry: String,
    val toughness: String,
    val gear: Map<String, String>, // TODO: allow Array<String> as values
    val salvage: Salvage,
    val specialAbilities: Map<String, String> // TODO: allow Array<String> as values
) {
    val sortedSkills: Map<String, ModifiedDie>
        get() = skills.toSortedMap(baseSkillsFirst andThen byNaturalOrder)
}

