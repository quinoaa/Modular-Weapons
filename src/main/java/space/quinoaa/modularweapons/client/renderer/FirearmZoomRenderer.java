package space.quinoaa.modularweapons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class FirearmZoomRenderer {

    public static void render(LocalPlayer local, ItemStack pItemStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, ItemRenderer itemRenderer, int pSeed) {

        pPoseStack.translate(-.56, 0.75, 0);
        itemRenderer.renderStatic(local, pItemStack, ItemDisplayContext.NONE, false,
                pPoseStack, pBuffer, local.level(), pSeed, OverlayTexture.NO_OVERLAY, local.getId() + pDisplayContext.ordinal());

    }
}
