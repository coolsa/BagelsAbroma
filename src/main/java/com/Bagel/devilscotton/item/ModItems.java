package com.Bagel.devilscotton.item;

import com.Bagel.devilscotton.DevilsCotton;
import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DevilsCotton.MOD_ID);

    public static final RegistryObject<Item> BESTIE_ITEM = ITEMS.register("bestie_item",
            () -> new Item(new Item.Properties().group(ItemGroup.MISC).food(Foods.GOLDEN_APPLE)));






    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
