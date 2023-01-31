package de.horstblocks.homes.utils

import org.bukkit.Material
import org.bukkit.block.Biome

val Biome.definingMaterial: Material
    get() {
        return when (this) {
            Biome.OCEAN -> Material.WATER
            Biome.PLAINS -> Material.GRASS_BLOCK
            Biome.DESERT -> Material.SAND
            Biome.WINDSWEPT_HILLS -> Material.STONE
            Biome.FOREST -> Material.OAK_LOG
            Biome.TAIGA -> Material.SPRUCE_LOG
            Biome.SWAMP -> Material.SLIME_BLOCK
            Biome.MANGROVE_SWAMP -> Material.MANGROVE_LOG
            Biome.RIVER -> Material.SAND
            Biome.NETHER_WASTES -> Material.NETHERRACK
            Biome.THE_END -> Material.END_STONE
            Biome.FROZEN_OCEAN -> Material.ICE
            Biome.FROZEN_RIVER -> Material.ICE
            Biome.SNOWY_PLAINS -> Material.SNOW_BLOCK
            Biome.MUSHROOM_FIELDS -> Material.RED_MUSHROOM_BLOCK
            Biome.BEACH -> Material.SAND
            Biome.JUNGLE -> Material.JUNGLE_LOG
            Biome.SPARSE_JUNGLE -> Material.JUNGLE_LOG
            Biome.DEEP_OCEAN -> Material.WATER
            Biome.STONY_SHORE -> Material.STONE
            Biome.SNOWY_BEACH -> Material.SNOW_BLOCK
            Biome.BIRCH_FOREST -> Material.BIRCH_LOG
            Biome.DARK_FOREST -> Material.DARK_OAK_LOG
            Biome.SNOWY_TAIGA -> Material.SPRUCE_LOG
            Biome.OLD_GROWTH_PINE_TAIGA -> Material.SPRUCE_LOG
            Biome.WINDSWEPT_FOREST -> Material.OAK_LOG
            Biome.SAVANNA -> Material.ACACIA_LOG
            Biome.SAVANNA_PLATEAU -> Material.ACACIA_LOG
            Biome.BADLANDS -> Material.RED_SAND
            Biome.WOODED_BADLANDS -> Material.RED_SAND
            Biome.SMALL_END_ISLANDS -> Material.END_STONE
            Biome.END_MIDLANDS -> Material.END_STONE
            Biome.END_HIGHLANDS -> Material.END_STONE
            Biome.END_BARRENS -> Material.END_STONE
            Biome.WARM_OCEAN -> Material.WATER
            Biome.LUKEWARM_OCEAN -> Material.WATER
            Biome.COLD_OCEAN -> Material.WATER
            Biome.DEEP_LUKEWARM_OCEAN -> Material.WATER
            Biome.DEEP_COLD_OCEAN -> Material.WATER
            Biome.DEEP_FROZEN_OCEAN -> Material.WATER
            Biome.THE_VOID -> Material.BEDROCK
            Biome.SUNFLOWER_PLAINS -> Material.GRASS_BLOCK
            Biome.WINDSWEPT_GRAVELLY_HILLS -> Material.GRAVEL
            Biome.FLOWER_FOREST -> Material.GRASS_BLOCK
            Biome.ICE_SPIKES -> Material.PACKED_ICE
            Biome.OLD_GROWTH_BIRCH_FOREST -> Material.BIRCH_LOG
            Biome.OLD_GROWTH_SPRUCE_TAIGA -> Material.SPRUCE_LOG
            Biome.WINDSWEPT_SAVANNA -> Material.ACACIA_LOG
            Biome.ERODED_BADLANDS -> Material.RED_SAND
            Biome.BAMBOO_JUNGLE -> Material.BAMBOO
            Biome.SOUL_SAND_VALLEY -> Material.SOUL_SAND
            Biome.CRIMSON_FOREST -> Material.CRIMSON_STEM
            Biome.WARPED_FOREST -> Material.WARPED_STEM
            Biome.BASALT_DELTAS -> Material.BASALT
            Biome.DRIPSTONE_CAVES -> Material.DRIPSTONE_BLOCK
            Biome.LUSH_CAVES -> Material.CLAY
            Biome.DEEP_DARK -> Material.SCULK_CATALYST
            Biome.MEADOW -> Material.GRASS_BLOCK
            Biome.GROVE -> Material.GRASS_BLOCK
            Biome.SNOWY_SLOPES -> Material.SNOW_BLOCK
            Biome.FROZEN_PEAKS -> Material.SNOW_BLOCK
            Biome.JAGGED_PEAKS -> Material.STONE
            Biome.STONY_PEAKS -> Material.STONE
            else -> Material.GRASS_BLOCK
        }
    }