package xyz.przemyk.geysermod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GeyserMod.MODID)
public class GeyserMod {
    public static final String MODID = "geysermod";

    public GeyserMod() {
        Registration.init();
    }
}
