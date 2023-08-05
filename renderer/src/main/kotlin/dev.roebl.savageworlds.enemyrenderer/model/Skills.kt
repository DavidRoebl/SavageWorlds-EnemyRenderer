package dev.roebl.savageworlds.enemyrenderer.model

val baseSkillsFirst: Comparator<String> = Comparator { first, second ->
    first.weight - second.weight
}

val byNaturalOrder: Comparator<String> = Comparator.naturalOrder()

infix fun Comparator<String>.andThen(bySecond: Comparator<String>): Comparator<String> {
    return this.thenComparing(bySecond)
}

private val String.weight: Int
    get() {
        return when (this) {
            "common knowledge" -> 1
            "athletics" -> 2
            "stealth" -> 3
            "persuasion" -> 4
            "notice" -> 5
            else -> first().code
        }
    }