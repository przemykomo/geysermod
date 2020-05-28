package xyz.przemyk.geysermod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GeyserMod.MODID)
public class GeyserMod {
    public static final String MODID = "geysermod";

    public GeyserMod() {
        Registration.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        Registration.commonSetup();
    }
}
