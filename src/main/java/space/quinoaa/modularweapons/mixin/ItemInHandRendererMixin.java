package space.quinoaa.modularweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.quinoaa.modularweapons.client.interaction.WeaponUseHandler;
import space.quinoaa.modularweapons.client.renderer.FirearmZoomRenderer;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Shadow @Final private ItemRenderer itemRenderer;

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    public void renderWeapon(LivingEntity pEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pSeed, CallbackInfo ci){
        if(!WeaponUseHandler.isZooming) return;
        var local = Minecraft.getInstance().player;
        if(pEntity != local) return;
        if(pLeftHand) return;

        ci.cancel();
        FirearmZoomRenderer.render(local, pItemStack, pDisplayContext, pPoseStack, pBuffer, itemRenderer, pSeed);
    }
}
