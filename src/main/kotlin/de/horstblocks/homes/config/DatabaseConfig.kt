package de.horstblocks.homes.config

lateinit var dbConfig: ConfigFile
    private set

fun loadDatabaseConfigFile() {
    // The default credentials match the ones from the docker-compose.dev.yml file

    dbConfig = ConfigFile("db.yml", hashMapOf(
        "host" to "localhost",
        "port" to 3306,
        "user" to "test",
        "pass" to "test",
        "database" to "test"
    ))
}

