package com.github.coolsa.devils_cotton_mod;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.NoiseDependantDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("devils_cotton_mod")
public class DevilsCotton {
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public static final String MOD_ID = "devil_cotton_mod";
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			MOD_ID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

	public static final RegistryObject<Block> ABROMA_AUGUSTUM_BLOCK = BLOCKS.register("abroma_augustum",
			() -> new FlowerBlock(MobEffects.REGENERATION, 8, BlockBehaviour.Properties.copy(Blocks.OXEYE_DAISY)));
	// FIXME: Use the forge defined flower thing.
	public static final RegistryObject<Block> POTTED_ABROMA_AUGUSTUM_BLOCK = BLOCKS.register("potted_abroma_augustum",
			() -> new FlowerPotBlock(ABROMA_AUGUSTUM_BLOCK.get(),
					BlockBehaviour.Properties.copy(Blocks.POTTED_OXEYE_DAISY)));

	public static final RegistryObject<Item> ABROMA_AUGUSTUM_ITEM = ITEMS.register("abroma_augustum",
			() -> new BlockItem(ABROMA_AUGUSTUM_BLOCK.get(),
					new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

	public static ConfiguredFeature<?, ?> ABROMA_AUGUSTUM_FEATURE;
	
	public DevilsCotton() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the rendering thing for flowers.
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

		//register the biome setup event.
		MinecraftForge.EVENT_BUS.addListener(this::loadBiome);

//		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerFeatures);
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		// some preinit code
		event.enqueueWork(() -> {
			ABROMA_AUGUSTUM_FEATURE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
					new ResourceLocation(MOD_ID, "abroma_augustum"),
					Feature.FLOWER
							.configured((new RandomPatchConfiguration.GrassConfigurationBuilder(
									new SimpleStateProvider(ABROMA_AUGUSTUM_BLOCK.get().defaultBlockState()),
									SimpleBlockPlacer.INSTANCE)).tries(6).build())
							.decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP).squared()
							.decorated(FeatureDecorator.COUNT_NOISE
									.configured(new NoiseDependantDecoratorConfiguration(-0.8D, 15, 4))));
		});
//		LOGGER.info("HELLO FROM PREINIT");
//		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
	}

	@SubscribeEvent
	public void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ItemBlockRenderTypes.setRenderLayer(ABROMA_AUGUSTUM_BLOCK.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(POTTED_ABROMA_AUGUSTUM_BLOCK.get(), RenderType.cutout());
		});
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void loadBiome(final BiomeLoadingEvent event) {
		if (event.getCategory() == BiomeCategory.PLAINS || event.getCategory() == BiomeCategory.TAIGA
				|| event.getCategory() != BiomeCategory.FOREST) {
			event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION).add(() -> ABROMA_AUGUSTUM_FEATURE);
		}
	}
}
