package com.Bagel.devilscotton.world.gen;

import com.Bagel.devilscotton.block.ModBlocks;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;


public class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> DEVILS_COTTON_CONFIG = Feature.FLOWER.withConfiguration(
                    (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.DEVILS_COTTON.get().getDefaultState()),
                            SimpleBlockPlacer.PLACER)).tries(12).preventProjection().build())
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
            .withPlacement(Placement.COUNT_EXTRA.configure(
                    new AtSurfaceWithExtraConfig(0, 0.04f, 2)));
}