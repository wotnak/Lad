package net.wotnak.lad.commands

import net.wotnak.lad.generator.GeneratorSettings
import net.wotnak.lad.generator.IslandGenerator
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class LadCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val loc = sender.location
            val gen = IslandGenerator()
            val settings = GeneratorSettings()
            var seed = System.nanoTime()
            if (args.isNotEmpty()) {
                for (arg in args) {
                    if (!arg.contains(':',true)) continue
                    val n = arg.split(":")[0]
                    val v = arg.split(":")[1]
                    when(n) {
                        "w" -> settings.islandWidth = v.toInt()
                        "l" -> settings.islandLength = v.toInt()
                        "sd" ->  settings.stretchDown = v.toInt()
                        "su" -> settings.stretchUp = v.toInt()
                        "o" -> settings.octaves = v.toInt()
                        "f" -> settings.frequency = v.toDouble()
                        "a" -> settings.amplitude = v.toDouble()
                        "s" -> settings.scale = v.toDouble()
                        "sampleDown" -> settings.sampleDown = v.toDouble()
                        "sampleUp" -> settings.sampleUp = v.toDouble()
                        "seed" -> seed = v.hashCode().toLong()
                        "size" -> {
                            settings.islandWidth = v.toInt()
                            settings.islandLength = v.toInt()
                        }
                    }
                }
            }
            gen.generate(loc,settings,seed)
            return true
        }
        return false
    }
}
