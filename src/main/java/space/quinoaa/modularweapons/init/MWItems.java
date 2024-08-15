package space.quinoaa.modularweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.item.BaseAmmo;
import space.quinoaa.modularweapons.item.weapon.MultiBarrelGun;
import space.quinoaa.modularweapons.item.weapon.Rifle;
import space.quinoaa.modularweapons.item.ammo.IncendiaryAmmo;
import space.quinoaa.modularweapons.item.part.BarrelPart;
import space.quinoaa.modularweapons.item.part.ScopePart;

import java.util.function.Supplier;

public class MWItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModularWeapons.MODID);

    public static final RegistryObject<BlockItem> WEAPON_WORKBENCH = REGISTRY.register("weapon_workbench", ()->new BlockItem(MWBlocks.WEAPON_WORKBENCH.get(), new Item.Properties()));

    public static final RegistryObject<Rifle> RIFLE = register("rifle", () -> new Rifle(new Item.Properties()));
    public static final RegistryObject<MultiBarrelGun> MULTI_BARREL_GUN = register("multi_barrel_gun", () -> new MultiBarrelGun(new Item.Properties()));

    public static final RegistryObject<ScopePart> SCOPE_X2 = register("scope_x2", () -> new ScopePart(new Item.Properties(), 2));
    public static final RegistryObject<ScopePart> SCOPE_X4 = register("scope_x4", () -> new ScopePart(new Item.Properties(), 4));
    public static final RegistryObject<ScopePart> SCOPE_X8 = register("scope_x8", () -> new ScopePart(new Item.Properties(), 8));

    public static final RegistryObject<BarrelPart> BARREL = register("barrel", () -> new BarrelPart(new Item.Properties(), 5, 10, 40));
    public static final RegistryObject<BarrelPart> GUNNER_BARREL = register("gunner_barrel", () -> new BarrelPart(new Item.Properties(), 1, 4, 30));
    public static final RegistryObject<BarrelPart> SNIPER_BARREL = register("sniper_barrel", () -> new BarrelPart(new Item.Properties(), 20, 40, 200));

    public static final RegistryObject<BaseAmmo> AMMO = register("ammo", () -> new BaseAmmo(new Item.Properties().durability(32)));
    public static final RegistryObject<BaseAmmo> LARGE_AMMO = register("large_ammo", () -> new BaseAmmo(new Item.Properties().durability(256)));
    public static final RegistryObject<BaseAmmo> INCENDIARY_AMMO = register("incendiary_ammo", () -> new IncendiaryAmmo(new Item.Properties().durability(32)));


    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> accept){
        return REGISTRY.register(name, accept);
    }


    public static void init(IEventBus bus){
        REGISTRY.register(bus);

        DeferredRegister<CreativeModeTab> reg = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "modularweapons");

        reg.register("main", ()->CreativeModeTab.builder()
                .title(Component.translatable("modularweapons.tab.title"))
                .displayItems((param, out)->{
                    for (RegistryObject<Item> entry : REGISTRY.getEntries()) {
                        out.accept(new ItemStack(entry.get()));
                    }
                })
                .icon(()->new ItemStack(MWItems.WEAPON_WORKBENCH.get()))
                .build());

        reg.register(bus);
    }

}
