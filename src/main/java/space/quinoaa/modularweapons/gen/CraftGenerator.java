package space.quinoaa.modularweapons.gen;


import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeRecipeProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.init.MWItems;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ModularWeapons.MODID)
public class CraftGenerator extends RecipeProvider{


    public CraftGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @SubscribeEvent
    public static void generateCraft(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), (Factory<DataProvider>) CraftGenerator::new);

    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.WEAPON_WORKBENCH.get())
                .pattern("iii")
                .pattern("bbb")
                .define('i', Items.IRON_INGOT)
                .define('b', Items.IRON_BLOCK)
                .unlockedBy("weapon_workbench", RecipeUnlockedTrigger.unlocked(MWItems.WEAPON_WORKBENCH.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.AMMO.get())
                .pattern("ii")
                .pattern("gi")
                .define('i', Items.IRON_NUGGET)
                .define('g', Items.GUNPOWDER)
                .unlockedBy("ammo", RecipeUnlockedTrigger.unlocked(MWItems.AMMO.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.INCENDIARY_AMMO.get())
                .pattern("ii ")
                .pattern("gib")
                .define('i', Items.IRON_NUGGET)
                .define('g', Items.GUNPOWDER)
                .define('b', Items.BLAZE_POWDER)
                .unlockedBy("incendiary_ammo", RecipeUnlockedTrigger.unlocked(MWItems.AMMO.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.LARGE_AMMO.get())
                .pattern("iii")
                .pattern("gii")
                .define('i', Items.IRON_NUGGET)
                .define('g', Items.GUNPOWDER)
                .unlockedBy("large_ammo", RecipeUnlockedTrigger.unlocked(MWItems.AMMO.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.BARREL.get())
                .pattern("iii")
                .define('i', Items.IRON_NUGGET)
                .unlockedBy("barrel", RecipeUnlockedTrigger.unlocked(MWItems.BARREL.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.SNIPER_BARREL.get())
                .pattern("iib")
                .define('i', Items.IRON_NUGGET)
                .define('b', Items.IRON_BLOCK)
                .unlockedBy("sniper_barrel", RecipeUnlockedTrigger.unlocked(MWItems.SNIPER_BARREL.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.GUNNER_BARREL.get())
                .pattern("iir")
                .define('i', Items.IRON_NUGGET)
                .define('r', Items.REDSTONE)
                .unlockedBy("gunner_barrel", RecipeUnlockedTrigger.unlocked(MWItems.GUNNER_BARREL.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.SCOPE_X2.get())
                .pattern("iii")
                .pattern("igi")
                .pattern("iii")
                .define('i', Items.IRON_INGOT)
                .define('g', Items.GLASS)
                .unlockedBy("scope2", RecipeUnlockedTrigger.unlocked(MWItems.SCOPE_X2.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.SCOPE_X4.get())
                .pattern("idi")
                .pattern("dgd")
                .pattern("idi")
                .define('i', Items.IRON_INGOT)
                .define('d', Items.DIAMOND)
                .define('g', Items.GLASS)
                .unlockedBy("scope4", RecipeUnlockedTrigger.unlocked(MWItems.SCOPE_X4.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.SCOPE_X8.get())
                .pattern("iii")
                .pattern("igi")
                .pattern("iii")
                .define('i', Items.DIAMOND)
                .define('g', Items.GLASS)
                .unlockedBy("scope8", RecipeUnlockedTrigger.unlocked(MWItems.SCOPE_X8.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.RIFLE.get())
                .pattern("iii")
                .pattern("w  ")
                .define('i', Items.IRON_INGOT)
                .define('w', ItemTags.PLANKS)
                .unlockedBy("rifle", RecipeUnlockedTrigger.unlocked(MWItems.RIFLE.getId()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MWItems.MULTI_BARREL_GUN.get())
                .pattern("iib")
                .pattern("w  ")
                .define('i', Items.IRON_INGOT)
                .define('b', Items.IRON_BLOCK)
                .define('w', ItemTags.PLANKS)
                .unlockedBy("multi_barrel", RecipeUnlockedTrigger.unlocked(MWItems.MULTI_BARREL_GUN.getId()))
                .save(pWriter);
    }
}
