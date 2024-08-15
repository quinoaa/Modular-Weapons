package space.quinoaa.modularweapons.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.registries.ForgeRegistries;
import space.quinoaa.modularweapons.item.BaseFirearm;

import java.util.HashMap;
import java.util.Map;

public class FirearmCustomRenderer extends BlockEntityWithoutLevelRenderer {
    public FirearmCustomRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    public static void onRenderItem(BaseFirearm firearm, ItemStack pStack, ItemDisplayContext pDisplayContext, boolean pLeftHand, PoseStack pose, MultiBufferSource pBuffer, int pCombinedLight, int pCombinedOverlay) {
        var itemRender = Minecraft.getInstance().getItemRenderer();

        var model = itemRender.getModel(pStack, null, null, 0);
        itemRender.render(pStack, pDisplayContext, pLeftHand, pose, pBuffer, pCombinedLight, pCombinedOverlay, model);
        pose.pushPose();
        model.applyTransform(pDisplayContext, pose, pLeftHand);

        for (BaseFirearm.Slot<?> slot : firearm.getSlots()) {
            var item = firearm.getPart(pStack, slot);
            if(item == null) continue;

            pose.pushPose();
            var off = slot.offset();
            pose.translate(off.x, off.y, off.z);

            var stack = getStack(item);
            model = itemRender.getModel(stack, null, null, 0);
            itemRender.render(pStack, ItemDisplayContext.NONE, pLeftHand, pose, pBuffer, pCombinedLight, pCombinedOverlay, model);

            pose.popPose();
        }
        pose.popPose();
    }


    private static Map<Item, ItemStack> cache = new HashMap<>();

    private static ItemStack getStack(Item item){
        if(cache.containsKey(item)) return cache.get(item);
        ItemStack stack = new ItemStack(item);
        cache.put(item, stack);
        return stack;
    }
}
