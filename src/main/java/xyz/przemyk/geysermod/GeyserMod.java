package xyz.przemyk.geysermod;

import net.minecraftforge.fml.common.Mod;

@Mod(GeyserMod.MODID)
public class GeyserMod {
    public static final String MODID = "geysermod";

    public GeyserMod() {
        Registration.init();
    }
}
