package de.horstblocks.homes.db

import java.sql.Connection
import java.sql.DriverManager
import de.horstblocks.homes.config.dbConfig

class DatabaseConnection {
    private var connection: Connection? = null

    fun getConnection(): Connection {
        openConnection()
        return connection!!
    }

    fun openConnection() {
        if (connection != null && !connection!!.isClosed) {
            return
        }

        connection = DriverManager.getConnection(
            "jdbc:mysql://${dbConfig.get<String>("host")}:${dbConfig.get<Int>("port")}/" +
                    "${dbConfig.get<String>("database")}?" +
                    "user=${dbConfig.get<String>("user")}&password=${dbConfig.get<String>("pass")}"
        )
    }

    fun runScript(scriptName: String) {
        val script = javaClass.classLoader.getResourceAsStream("sql/$scriptName")
        val sql = script!!.bufferedReader().use { it.readText() }
        getConnection().createStatement().use { it.execute(sql) }
    }

    fun closeConnection() {
        connection?.close()
        connection = null
    }
}