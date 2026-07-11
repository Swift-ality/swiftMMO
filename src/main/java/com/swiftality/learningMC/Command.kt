package com.swiftality.learningMC

import com.mojang.brigadier.arguments.StringArgumentType
import com.swiftality.learningMC.items.ItemHandler
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents

object Command {

    @JvmStatic
    fun register(plugin: LearningMC)  {
        plugin.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(Commands.literal("swiftmmo")
                .then(Commands.literal("give")
                        .then(Commands.argument("player", ArgumentTypes.player())
                            .then(
                                Commands.argument("item-type", StringArgumentType.word())
                                    .suggests { _, builder ->
                                        plugin.config.getKeys(false).forEach { type ->
                                            builder.suggest(type)
                                        }
                                        builder.buildFuture()
                                    }
                                    .then(
                                        Commands.argument("item-name", StringArgumentType.word())
                                            .suggests { context, builder ->
                                                val itemType = StringArgumentType.getString(context, "item-type")

                                                plugin.config.getConfigurationSection(itemType)
                                                    ?.getKeys(false)
                                                    ?.forEach { itemName ->
                                                        builder.suggest(itemName)
                                                    }
                                                builder.buildFuture()
                                            }
                                            .executes { context ->
                                                ItemHandler.giveItem(context)
                                            }
                                    )
                            )
                        )
                    )
                .then(
                    Commands.literal("reload")
                        .executes {
                            ConfigManager.reload()
                            1
                        }
                )
                .build()
            )
        }
    }
}

