package space.quinoaa.modularweapons.gen;

import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.init.MWBlocks;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ModularWeapons.MODID)
public class BlockstateGenerator extends BlockStateProvider {

    public BlockstateGenerator(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }


    @SubscribeEvent
    public static void generateBlockstates(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), (Factory<DataProvider>) out->new BlockstateGenerator(out, ModularWeapons.MODID, event.getExistingFileHelper()));
    }


    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(MWBlocks.WEAPON_WORKBENCH.get(), models().getExistingFile(MWBlocks.WEAPON_WORKBENCH.getId()));
    }
}
