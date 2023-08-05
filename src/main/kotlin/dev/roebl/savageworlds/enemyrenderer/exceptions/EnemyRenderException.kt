package dev.roebl.savageworlds.enemyrenderer.exceptions
sealed class EnemyRenderException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

class InputException(message: String, cause: Throwable) : EnemyRenderException(message, cause)
