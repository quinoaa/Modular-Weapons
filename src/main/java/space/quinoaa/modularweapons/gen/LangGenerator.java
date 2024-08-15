package space.quinoaa.modularweapons.gen;

import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.init.MWItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ModularWeapons.MODID)
public class LangGenerator extends LanguageProvider {
    public LangGenerator(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }



    @SubscribeEvent
    public static void generateLang(GatherDataEvent event) {
        event.getGenerator().addProvider(event.includeServer(), (Factory<DataProvider>) out->new LangGenerator(out, ModularWeapons.MODID, "en_us"));
    }

    @Override
    protected void addTranslations() {
        add("modularweapons.tab.title", "Modular Weapons");
        add("modularweapons.workbench.title", "Weapon Workbench");
        add("block.modularweapons.weapon_workbench", "Weapon Workbench");

        add(MWItems.RIFLE.get(), "Rifle");
        add(MWItems.MULTI_BARREL_GUN.get(), "Multi-barrel gun");
        add(MWItems.SCOPE_X2.get(), "Scope x2");
        add(MWItems.SCOPE_X4.get(), "Scope x4");
        add(MWItems.SCOPE_X8.get(), "Scope x8");
        add(MWItems.BARREL.get(), "Barrel");
        add(MWItems.GUNNER_BARREL.get(), "Gunner barrel");
        add(MWItems.SNIPER_BARREL.get(), "Sniper barrel");
        add(MWItems.AMMO.get(), "Ammo");
        add(MWItems.LARGE_AMMO.get(), "Large ammo");
        add(MWItems.INCENDIARY_AMMO.get(), "Incendiary ammo");
    }
}
