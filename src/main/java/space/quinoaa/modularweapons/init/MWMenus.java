package space.quinoaa.modularweapons.init;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.menu.WeaponWorkbenchMenu;

public class MWMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModularWeapons.MODID);

    public static final RegistryObject<MenuType<WeaponWorkbenchMenu>> WEAPON_WORKBENCH = REGISTRY.register("weapon_workbench", ()-> IForgeMenuType.create(WeaponWorkbenchMenu::new));

    public static void init(IEventBus bus){
        REGISTRY.register(bus);
    }
}
