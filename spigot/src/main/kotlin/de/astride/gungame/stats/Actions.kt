package de.astride.gungame.stats

import de.astride.gungame.event.GunGameAddedActionEvent
import net.darkdevelopers.darkbedrock.darkness.universal.functions.call
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 11.04.2019 03:52.
 * Current Version: 1.0 (11.04.2019 - 12.04.2019)
 */
class Actions(val uuid: UUID, input: Collection<Action> = emptyList()) : ArrayList<Action>(input) {

    override fun add(element: Action): Boolean {
        GunGameAddedActionEvent(uuid, element).call()
        return super.add(element)
    }

}