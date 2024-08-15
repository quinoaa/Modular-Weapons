package space.quinoaa.modularweapons.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.quinoaa.modularweapons.client.renderer.FirearmCustomRenderer;
import space.quinoaa.modularweapons.item.BaseFirearm;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Unique
    private boolean rendering = false;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(ItemStack pItemStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, int pCombinedOverlay, BakedModel pModel, CallbackInfo ci){
        if(rendering) return;
        if(!(pItemStack.getItem() instanceof BaseFirearm firearm)) return;

        rendering = true;
        ci.cancel();
        FirearmCustomRenderer.onRenderItem(firearm, pItemStack, pDisplayContext, pLeftHand, pPoseStack, pBuffer, pCombinedLight, pCombinedOverlay);
        rendering = false;

    }

}
