package de.horstblocks.homes.db

import de.horstblocks.homes.Homes
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*


class HomeDAO(
    var id: Int? = null,
    var owner: String,
    var name: String,
    var world: String,
    var x: Float,
    var y: Float,
    var z: Float,
    var yaw: Float,
    var pitch: Float,
    var material: String,
) {
    override fun toString(): String {
        return "HomeDAO(id=$id, owner='$owner', name='$name', world='$world', x=$x, y=$y, z=$z, yaw=$yaw, pitch=$pitch, material='$material')"
    }

    fun setOwner(player: Player) {
        owner = player.uniqueId.toString()
    }

    fun getOwner(): Player {
        return Homes.instance.server.getOfflinePlayer(UUID.fromString(owner)).player
            ?: throw IllegalStateException("Player is not online!")
    }

    fun setMaterial(material: Material) {
        this.material = material.name
    }

    fun getMaterial(): Material {
        return Material.valueOf(material)
    }

    fun save() {
        if (id == null) {
            insertHome()
        } else {
            updateHome()
        }
    }

    private fun insertSelf(statement: PreparedStatement) {
        statement.setString(1, owner)
        statement.setString(2, name)
        statement.setString(3, world)
        statement.setFloat(4, x)
        statement.setFloat(5, y)
        statement.setFloat(6, z)
        statement.setFloat(7, yaw)
        statement.setFloat(8, pitch)
        statement.setString(9, material)
    }

    private fun updateHome() {
        Homes.instance.database.getConnection().use { connection ->
            connection.prepareStatement("UPDATE homes SET uuid = ?, name = ?, world = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ?, material = ? WHERE id = ?")
                .use { statement ->
                    insertSelf(statement)
                    statement.setInt(10, id!!)
                    statement.executeUpdate()
                }
        }
    }

    private fun insertHome() {
        Homes.instance.database.getConnection().use { connection ->
            connection.prepareStatement("INSERT INTO homes (uuid, name, world, x, y, z, yaw, pitch, material) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)
                .use { statement ->
                    insertSelf(statement)
                    statement.setString(9, material)
                    statement.executeUpdate()

                    statement.use {
                        val resultSet: ResultSet = it.generatedKeys
                        resultSet.next()
                        id = resultSet.getLong(1).toInt()
                    }
                }
        }
    }

    fun delete() {
        if (id == null) {
            return
        }

        Homes.instance.database.getConnection().use { connection ->
            connection.prepareStatement("DELETE FROM homes WHERE id = ?")
                .use { statement ->
                    statement.setInt(1, id!!)
                    statement.executeUpdate()
                }
        }
    }

    fun toLocation(): Location {
        return Location(
            Homes.instance.server.getWorld(world),
            x.toDouble(),
            y.toDouble(),
            z.toDouble(),
            yaw,
            pitch
        )
    }

    companion object {
        fun hasHome(uuid: UUID, name: String): Boolean {
            Homes.instance.database.getConnection().use { connection ->
                connection.prepareStatement("SELECT * FROM homes WHERE uuid = ? AND name = ?")
                    .use { statement ->
                        statement.setString(1, uuid.toString())
                        statement.setString(2, name)
                        statement.executeQuery().use { resultSet ->
                            return resultSet.next()
                        }
                    }
            }
        }

        private fun fromResultSet(resultSet: ResultSet): HomeDAO {
            return HomeDAO(
                resultSet.getInt("id"),
                resultSet.getString("uuid"),
                resultSet.getString("name"),
                resultSet.getString("world"),
                resultSet.getFloat("x"),
                resultSet.getFloat("y"),
                resultSet.getFloat("z"),
                resultSet.getFloat("yaw"),
                resultSet.getFloat("pitch"),
                resultSet.getString("material"),
            )
        }

        fun getHome(uuid: UUID, name: String): HomeDAO? {
            Homes.instance.database.getConnection().use { connection ->
                connection.prepareStatement("SELECT * FROM homes WHERE uuid = ? AND name = ?")
                    .use { statement ->
                        statement.setString(1, uuid.toString())
                        statement.setString(2, name)
                        statement.executeQuery().use { resultSet ->
                            if (resultSet.next()) {
                                return fromResultSet(resultSet)
                            }
                        }
                    }
            }
            return null
        }

        fun getHomes(uniqueId: UUID): List<HomeDAO> {
            val homes = mutableListOf<HomeDAO>()
            Homes.instance.database.getConnection().use { connection ->
                connection.prepareStatement("SELECT * FROM homes WHERE uuid = ?")
                    .use { statement ->
                        statement.setString(1, uniqueId.toString())
                        statement.executeQuery().use { resultSet ->
                            while (resultSet.next()) {
                                homes.add(fromResultSet(resultSet))
                            }
                        }
                    }
            }
            return homes
        }
    }
}