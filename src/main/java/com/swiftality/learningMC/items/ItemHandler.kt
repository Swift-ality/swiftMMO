package com.swiftality.learningMC.items

import com.mojang.brigadier.context.CommandContext
import com.swiftality.learningMC.LearningMC
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object ItemHandler {

    private lateinit var plugin: LearningMC

    fun setup(plugin: LearningMC){
        this.plugin = plugin
    }

    fun giveItem(context: CommandContext<CommandSourceStack>) : Int{
        //VARIABLES
        val sender = context.source.sender
        val resolver = context.getArgument("player", PlayerSelectorArgumentResolver::class.java)

        val player = resolver.resolve(context.source).single()
        val playerName = player.name
        val item = context.getArgument("item-name", String::class.java).toString()
        val itemType = context.getArgument("item-type", String::class.java).toString()

        //logic
        sender.sendMessage("[mmo] Hello $playerName you wanted $item")

        sender.sendMessage("[mmo] giving item")
        player.inventory.addItem(itemgiver(player, itemType, item)?: ItemStack(Material.AIR))

        return 1
    }

    fun itemgiver(player: Player, itemType: String, item: String) : ItemStack?{

        if(!plugin.config.contains("Items.${itemType}.${item}")) return null

        //val itemdata = plugin.config.getString("Items.${itemType}.${item}")
        val itemName = plugin.config.getString("Items.${itemType}.${item}.name") ?: return null
        val itemBase = plugin.config.getString("Items.${itemType}.${item}.base-item") ?: return null
        //val attributes = plugin.config.getString("Items.${itemType}.${item}.attributes") ?: "NULL"
        val intelligence = plugin.config.getLong("Items.${itemType}.${item}.attributes.intelligence")
        val strength = plugin.config.getLong("Items.${itemType}.${item}.attributes.strength")
        val agility = plugin.config.getLong("Items.${itemType}.${item}.attributes.agility")

        //DEBUG
        player.sendMessage("$itemName, $itemBase, $intelligence, $strength, $agility")

        val material = Material.matchMaterial(itemBase) ?: Material.AIR
        val item = ItemStack(material, 1)
        val meta = item.itemMeta

        meta.displayName(Component.text(itemName))
        meta.lore(listOf(
            Component.text("Strength: $strength").color(NamedTextColor.RED),
            Component.text("Intelligence: $intelligence").color(NamedTextColor.AQUA),
            Component.text("Agility: $agility").color(NamedTextColor.LIGHT_PURPLE)
        ))

        item.itemMeta = meta
        return item

    }
}