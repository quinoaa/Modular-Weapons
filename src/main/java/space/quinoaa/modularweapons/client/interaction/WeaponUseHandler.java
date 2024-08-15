package space.quinoaa.modularweapons.client.interaction;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.init.MWNetwork;
import space.quinoaa.modularweapons.item.BaseFirearm;
import space.quinoaa.modularweapons.network.serverbound.PacketFire;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ModularWeapons.MODID)
public class WeaponUseHandler {
    public static int cooldown = 0, zoomCooldown = 0;

    public static boolean isZooming = false;

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event){
        if(event.phase != TickEvent.Phase.START) return;

        if(cooldown > 0) cooldown--;
        if(zoomCooldown > 0) zoomCooldown--;

        if(isZooming){
            if(getFirearm(getItemStack()) == null) isZooming = false;
        }
    }



    private static ItemStack getItemStack(){
        var plr = Minecraft.getInstance().player;
        if(plr == null) return ItemStack.EMPTY;

        return plr.getMainHandItem();
    }

    private static @Nullable BaseFirearm getFirearm(ItemStack stack){
        if(stack.getItem() instanceof BaseFirearm arm) return arm;
        return null;
    }


    public static float getZoom(){
        var stack = getItemStack();
        var firearm = getFirearm(stack);
        var plr = Minecraft.getInstance().player;
        if(firearm == null || plr == null) return 1;
        var zoom = firearm.getZoom(stack);
        return zoom > 0.1 ? zoom : 1;
    }

    public static boolean attack() {
        var stack = getItemStack();
        var firearm = getFirearm(stack);
        var plr = Minecraft.getInstance().player;
        if(firearm == null || plr == null) return false;
        if(cooldown > 0) return true;

        firearm.attack(plr, stack, (t)->cooldown = t);

        MWNetwork.CHANNEL.sendToServer(new PacketFire(plr.getXRot(), plr.getYRot()));

        return true;
    }

    public static boolean use() {
        var stack = getItemStack();
        var firearm = getFirearm(stack);
        var plr = Minecraft.getInstance().player;
        if(firearm == null || plr == null) return false;

        if(zoomCooldown <= 0){
            plr.playSound(SoundEvents.SPYGLASS_USE, 1, 1);
            zoomCooldown = 20;
            isZooming = !isZooming;
        }

        return true;
    }
}
