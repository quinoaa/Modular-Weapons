package space.quinoaa.modularweapons.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.init.MWBlocks;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ModularWeapons.MODID)
public class TagGenerator extends TagsProvider<Block> {

    protected TagGenerator(PackOutput p_256596_, CompletableFuture<HolderLookup.Provider> p_256513_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256596_, Registries.BLOCK, p_256513_, modId, existingFileHelper);
    }

    @SubscribeEvent
    public static void generateTags(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), (Factory<DataProvider>) out->new TagGenerator(
                out,
                event.getLookupProvider(),
                ModularWeapons.MODID,
                event.getExistingFileHelper()
        ));

    }
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addOptional(MWBlocks.WEAPON_WORKBENCH.getId());
        tag(BlockTags.NEEDS_IRON_TOOL).addOptional(MWBlocks.WEAPON_WORKBENCH.getId());
    }
}
