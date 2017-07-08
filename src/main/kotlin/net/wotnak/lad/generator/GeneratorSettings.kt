package net.wotnak.lad.generator

data class GeneratorSettings(
        var islandWidth: Int = 16,
        var islandLength: Int = 16,
        var stretchDown: Int = 10,
        var stretchUp: Int = 2,

        var octaves: Int = 3,
        var sampleDown: Double = 0.1,
        var sampleUp: Double = 0.01,
        var frequency: Double = 1.0,
        var amplitude: Double = 1.0,
        var scale: Double = 1.0
)
