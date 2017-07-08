package net.wotnak.lad

import net.wotnak.lad.commands.LadCommand
import org.bukkit.plugin.java.JavaPlugin

class Lad : JavaPlugin() {

    override fun onEnable() {
        this.server.getPluginCommand("lad").executor = LadCommand()
    }

}