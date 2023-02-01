package de.horstblocks.homes.db

import de.horstblocks.homes.Homes
import org.bukkit.entity.Player
import java.util.UUID

class PlayerCacheDAO {
    private var uuid: UUID?
    var name: String

    constructor(
        uuid: UUID? = null,
        name: String
    ) {
        this.uuid = uuid
        this.name = name
    }

    constructor(player: Player) {
        this.uuid = player.uniqueId
        this.name = player.name
    }

    private fun isUUIDInDatabase(): Boolean {
        if (uuid == null) {
            return false
        }

        Homes.instance.database.getConnection().use { connection ->
            // Check if the uuid is already in the database

            connection.prepareStatement("SELECT * FROM players WHERE uuid = ?").use { statement ->
                statement.setString(1, uuid.toString())
                statement.executeQuery().use {
                    if (it.next()) {
                        return true
                    }
                }
            }
        }

        return false
    }

    fun ensureExists() {
        // Check if the uuid is already in the database
        if (!isUUIDInDatabase()) {
            // If not, insert it
            Homes.instance.database.getConnection().use {
                it.prepareStatement("INSERT INTO players (uuid, name) VALUES (?, ?)").use { statement ->
                    statement.setString(1, uuid.toString())
                    statement.setString(2, name)
                    statement.executeUpdate()
                }
            }
        } else {
            // If yes, update the name
            Homes.instance.database.getConnection().use {
                it.prepareStatement("UPDATE players SET name = ? WHERE uuid = ?").use { statement ->
                    statement.setString(1, name)
                    statement.setString(2, uuid.toString())
                    statement.executeUpdate()
                }
            }
        }

        Homes.instance.database.closeConnection()
    }

    fun fillUUID(): Result<UUID> {
        Homes.instance.database.getConnection().use { connection ->
            connection.prepareStatement("SELECT * FROM players WHERE name = ?").use { statement ->
                statement.setString(1, name)
                statement.executeQuery().use {
                    uuid = if (it.next()) {
                        UUID.fromString(it.getString("uuid"))
                    } else {
                        null
                    }
                }
            }
        }

        Homes.instance.database.closeConnection()

        if (uuid != null) {
            return Result.success(uuid!!)
        }

        return Result.failure(Exception("No UUID found for player $name"))
    }
}