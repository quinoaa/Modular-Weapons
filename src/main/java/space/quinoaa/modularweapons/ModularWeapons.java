package space.quinoaa.modularweapons;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import space.quinoaa.modularweapons.init.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModularWeapons.MODID)
public class ModularWeapons {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "modularweapons";
    // Directly reference a slf4j logger
    public static final Logger LOG = LogUtils.getLogger();


    public ModularWeapons() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MWBlockEntities.init(modEventBus);
        MWItems.init(modEventBus);
        MWBlocks.init(modEventBus);
        MWMenus.init(modEventBus);
        MWNetwork.init();
    }

}
