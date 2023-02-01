package de.horstblocks.homes.db

import de.horstblocks.homes.Homes
import org.bukkit.entity.Player
import java.util.UUID

class PlayerCacheDAO {
    var uuid: UUID
    var name: String

    constructor(
                uuid: UUID,
                name: String
    ) {
        this.uuid = uuid
        this.name = name
    }

    constructor(player: Player) {
        this.uuid = player.uniqueId
        this.name = player.name
    }

    fun ensureExists() {
        Homes.instance.database
    }
}