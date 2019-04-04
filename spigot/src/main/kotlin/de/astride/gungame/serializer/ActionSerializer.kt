package de.astride.gungame.serializer

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import de.astride.gungame.stats.Action
import java.lang.reflect.Type

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 04.04.2019 18:17.
 * Current Version: 1.0 (04.04.2019 - 04.04.2019)
 */
object ActionSerializer : JsonSerializer<Action> {

    override fun serialize(action: Action, type: Type, context: JsonSerializationContext): JsonElement =
        JsonObject().apply {
            addProperty("id", action.id)
            addProperty("timestamp", action.timestamp)
            add("meta", context.serialize(action.meta))
        }

}