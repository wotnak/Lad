package net.wotnak.lad.generator

import com.sun.javafx.geom.Vec2d
import org.bukkit.Location
import org.bukkit.util.noise.SimplexOctaveGenerator
import org.bukkit.Material.*
import org.bukkit.TreeType
import org.bukkit.block.BlockFace
import java.util.Random

class IslandGenerator {

    fun generate(loc: Location, settings: GeneratorSettings, seed: Long = System.nanoTime()) {
        val r = settings.islandWidth/2
        val center = Vec2d(r.toDouble(),r.toDouble())

        val rand = Random(seed)

        val gen = SimplexOctaveGenerator(seed,settings.octaves)
        gen.setScale(settings.scale)

        for (x in 0..settings.islandWidth-1) {
            for (z in 0..settings.islandLength-1) {
                val relativeX = x - r
                val relativeZ = z - r

                val distanceFromCenter = Math.sqrt(Math.pow(center.x - x,2.0) + Math.pow(center.y - z,2.0))/(r)

                var undergroundNoise = gen.noise(x.toDouble()*settings.sampleDown,z.toDouble()*settings.sampleDown,settings.frequency,settings.amplitude,true)
                undergroundNoise = invLerp(-1.0,1.0,undergroundNoise)
                undergroundNoise -= distanceFromCenter
                undergroundNoise *= settings.stretchDown

                var surfaceNoise = gen.noise(x.toDouble()*settings.sampleUp,z.toDouble()*settings.sampleUp,settings.frequency,settings.amplitude,true)
                surfaceNoise = invLerp(-1.0,1.0,surfaceNoise)
                surfaceNoise -= distanceFromCenter
                surfaceNoise *= settings.stretchUp

                for (yy in 1..undergroundNoise.toInt())
                    loc.block.getRelative(relativeX, -yy, relativeZ).type = STONE

                for (yy in 1..surfaceNoise.toInt())
                    loc.block.getRelative(relativeX, yy-1, relativeZ).type = GRASS
            }
        }

        for (x in 0..settings.islandWidth-1) {
            for (z in 0..settings.islandLength-1) {
                val xx = x - r
                val zz = z - r
                val block = loc.block.getRelative(xx,-1,zz)
                if (block.type == STONE) block.type = GRASS
                for (i in 2..4) {
                    val dirt = loc.block.getRelative(xx,-i,zz)
                    if (dirt.type == STONE)
                        if (rand.nextBoolean())
                            dirt.type = DIRT
                }

                for (i in 0..4) {
                    val air = loc.block.getRelative(xx,i,zz)
                    if (air.type == AIR)
                        if (air.getRelative(BlockFace.DOWN).type == GRASS)
                            if (rand.nextInt(100)==0) {
                                if (rand.nextBoolean())
                                    loc.world.generateTree(air.location,TreeType.BIG_TREE)
                                else
                                    loc.world.generateTree(air.location,TreeType.TREE)
                            }
                }
            }
        }
    }

    fun invLerp(min:Double,max:Double,value:Double) : Double {
        return (value - min) / (max - min)
    }

}