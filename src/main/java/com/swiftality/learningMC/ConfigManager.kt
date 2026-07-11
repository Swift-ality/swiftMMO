package com.swiftality.learningMC

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object ConfigManager: JavaPlugin() {

    private lateinit var plugin: LearningMC

    private lateinit var file: File
    private lateinit var config: FileConfiguration

    // finds the config
    fun setup(plugin: LearningMC){
        this.plugin = plugin
        file = File(plugin.dataFolder, "config.yml")
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun get(): FileConfiguration{
        file = File(plugin.dataFolder, "config.yml")
        config = YamlConfiguration.loadConfiguration(file)
        return config
    }
    fun save(): FileConfiguration{
        try {
            config.save(file)
        }catch (e: Exception) {
             logger.severe("Could not save config to ${file.name}")
            e.printStackTrace()
        }
        return config
    }
    fun reload(): FileConfiguration{
        plugin.reloadConfig()
        config = plugin.config
        logger.info("Config reloaded")
        return config
    }
}
