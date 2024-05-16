package dev.roebl.savageworlds.enemyrenderer.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

enum class Die {
    D4,
    D6,
    D8,
    D10,
    D12
}

class ModifiedDie(
    val die: Die,
    val modifier: String
) {

    override fun toString(): String {
        return "${die.name} ${modifier}"
    }
    class Serializer : JsonSerializer<ModifiedDie>, JsonDeserializer<ModifiedDie> {
        override fun serialize(src: ModifiedDie, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            return JsonPrimitive(src.toString())
        }
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): ModifiedDie {
            require(json.isJsonPrimitive)
            val primitive = json as JsonPrimitive
            require(primitive.isString)

            val components = primitive.asString.split(" ")

            val die = Die.valueOf(components.first())
            val modifier = if (components.size > 1) {
                components[1]
            } else {
                ""
            }
            return ModifiedDie(die, modifier)
        }
    }
}
