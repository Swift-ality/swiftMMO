package com.swiftality.learningMC

import com.swiftality.learningMC.items.ItemHandler
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class LearningMC : JavaPlugin() {

    lateinit var database: DatabaseHandler
        private set

    override fun onEnable() {
        Command.register(this)
        ItemHandler.setup(this)
        saveDefaultConfig()
        ConfigManager.setup(this)

        // db
        database = DatabaseHandler(File(dataFolder, "database.db"))
        database.createTables()
    }

    override fun onDisable() {
        // Plugin shutdown logic
        database.close()
    }
}