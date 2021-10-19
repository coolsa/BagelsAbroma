package com.Bagel.devilscotton.block;

import com.Bagel.devilscotton.DevilsCotton;
import com.Bagel.devilscotton.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.swing.*;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, DevilsCotton.MOD_ID);



    public static final RegistryObject<Block> DEVILS_COTTON = registerBlock("devils_cotton",
            () -> new FlowerBlock(Effects.REGENERATION, 10, AbstractBlock.Properties.from(Blocks.OXEYE_DAISY)));

    public static final RegistryObject<Block> POTTED_DEVILS_COTTON = BLOCKS.register("potted_devils_cotton",
            () -> new FlowerPotBlock(DEVILS_COTTON.get(), AbstractBlock.Properties.from(Blocks.POTTED_OXEYE_DAISY)));




    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItemm(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItemm(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ItemGroup.DECORATIONS)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}