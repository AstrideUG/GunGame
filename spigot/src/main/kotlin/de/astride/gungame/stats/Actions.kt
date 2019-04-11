package de.astride.gungame.stats

import de.astride.gungame.event.GunGameAddedActionEvent
import net.darkdevelopers.darkbedrock.darkness.universal.functions.call
import java.util.*

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 11.04.2019 03:52.
 * Current Version: 1.0 (11.04.2019 - 11.04.2019)
 */
class Actions(val uuid: UUID, input: Collection<Action> = emptyList()) : ArrayList<Action>(input) {

    operator fun plusAssign(element: Action) {
        this.add(element)
        GunGameAddedActionEvent(uuid, element).call()
    }

}