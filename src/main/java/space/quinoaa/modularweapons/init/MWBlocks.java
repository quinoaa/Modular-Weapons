package space.quinoaa.modularweapons.init;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.block.WeaponWorkbench;

public class MWBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, ModularWeapons.MODID);

    public static final RegistryObject<WeaponWorkbench> WEAPON_WORKBENCH = REGISTRY.register("weapon_workbench", ()->new WeaponWorkbench(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().isViewBlocking((a, b, c)->false)));


    public static void init(IEventBus bus){
        REGISTRY.register(bus);
    }
}
