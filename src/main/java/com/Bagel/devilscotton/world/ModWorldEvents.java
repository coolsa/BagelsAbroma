package com.Bagel.devilscotton.world;

import com.Bagel.devilscotton.DevilsCotton;
import com.Bagel.devilscotton.world.gen.ModFlowerGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DevilsCotton.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent (final BiomeLoadingEvent event) {
        ModFlowerGeneration.generateFlowers(event);

    }

}
