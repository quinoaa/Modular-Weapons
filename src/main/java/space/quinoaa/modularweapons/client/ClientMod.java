package space.quinoaa.modularweapons.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.client.screen.WeaponOverlay;
import space.quinoaa.modularweapons.client.screen.WeaponWorkbenchScreen;
import space.quinoaa.modularweapons.init.MWMenus;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = ModularWeapons.MODID)
public class ClientMod {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event){
        MenuScreens.register(MWMenus.WEAPON_WORKBENCH.get(), WeaponWorkbenchScreen::new);
    }

    @SubscribeEvent
    public static void initOverlay(RegisterGuiOverlaysEvent event){
        event.registerAboveAll("weapons", new WeaponOverlay());
    }

}
