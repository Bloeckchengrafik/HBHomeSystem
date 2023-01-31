package de.horstblocks.homes.config


import de.horstblocks.homes.Homes
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class ConfigFile(name: String, default: HashMap<String, Any>) {
    private val file = File(Homes.instance.dataFolder, name)
    private val yamlConfiguration: YamlConfiguration
    val data: HashMap<String, Any> = HashMap()

    init {
        File(file, "..").mkdirs()

        if (!file.exists()) {
            file.createNewFile()
        }

        yamlConfiguration = YamlConfiguration.loadConfiguration(file)

        default.forEach { (key, value) ->
            if (!yamlConfiguration.contains(key)) {
                yamlConfiguration.set(key, value)
            }
        }

        yamlConfiguration.getKeys(true).forEach { key ->
            data[key] = yamlConfiguration.get(key)!!
        }

        yamlConfiguration.save(file)
    }

    fun save() {
        data.forEach { (key, value) ->
            yamlConfiguration.set(key, value)
        }

        yamlConfiguration.save(file)
    }


    inline fun <reified T> get(key: String): T {
        data[key]?.let {
            if (it !is T) {
                throw ClassCastException("The value of the key \"$key\" is not of type ${T::class.java.simpleName}")
            }
            return it
        }
        return data[key]!! as T
    }

    operator fun set(key: String, value: String) {
        data[key] = value
    }

}
