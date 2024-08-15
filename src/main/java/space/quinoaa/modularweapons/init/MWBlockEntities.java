package space.quinoaa.modularweapons.init;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.block.entity.WeaponWorkbenchEntity;

import java.util.Set;

public class MWBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModularWeapons.MODID);

    public static final RegistryObject<BlockEntityType<WeaponWorkbenchEntity>> WEAPON_WORKBENCH = REGISTRY.register("weapon_workbench", ()->new BlockEntityType<>(WeaponWorkbenchEntity::new, Set.of(MWBlocks.WEAPON_WORKBENCH.get()), null));


    public static void init(IEventBus bus){
        REGISTRY.register(bus);
    }
}
