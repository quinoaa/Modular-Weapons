package space.quinoaa.modularweapons.client.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.init.MWNetwork;
import space.quinoaa.modularweapons.item.BaseFirearm;
import space.quinoaa.modularweapons.menu.WeaponWorkbenchMenu;
import space.quinoaa.modularweapons.network.serverbound.PacketSetFirearmPart;

public class WeaponWorkbenchScreen extends AbstractContainerScreen<WeaponWorkbenchMenu> {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(ModularWeapons.MODID, "textures/gui/container/weapon_workbench.png");

    public WeaponWorkbenchScreen(WeaponWorkbenchMenu pMenu, Inventory pPlayerInventory, Component title) {
        super(pMenu, pPlayerInventory, title);

        imageWidth = 176;
        imageHeight = 222;

        this.inventoryLabelX = 8;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics g, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(g);
        g.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics g, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(g, pMouseX, pMouseY, pPartialTick);

        renderWeapon(g);
        renderSlots(g, pMouseX, pMouseY);

        renderFloatingItemReal(g, menu.getCarried(), pMouseX - 8, pMouseY - 8, null);
        renderTooltip(g, pMouseX, pMouseY);
    }

    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        var item = menu.weaponSlot.getItem();
        if(!(item.getItem() instanceof BaseFirearm arm)) {
            super.renderTooltip(pGuiGraphics, pX, pY);
            return;
        }

        var pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 2500);

        boolean rendered = false;
        for (BaseFirearm.Slot<?> slot : arm.getSlots()) {
            if(isHovering(slot.x() + menu.weaponCenterX, slot.y() + menu.weaponCenterY, 16, 16, pX, pY)){
                rendered = true;
                var part = arm.getPart(item, slot);
                if(part == null) continue;
                ItemStack itemstack = new ItemStack(part);
                pGuiGraphics.renderTooltip(this.font, this.getTooltipFromContainerItem(itemstack), itemstack.getTooltipImage(), itemstack, pX, pY);
            }
        }
        if(!rendered) super.renderTooltip(pGuiGraphics, pX, pY);
        pose.popPose();
    }

    @Override
    public void renderFloatingItem(GuiGraphics pGuiGraphics, ItemStack pStack, int pX, int pY, String pText) {}

    public void renderFloatingItemReal(GuiGraphics pGuiGraphics, ItemStack pStack, int pX, int pY, String pText) {
        var pose = pGuiGraphics.pose();
        pose.pushPose();
        pose.translate(0, 0, 3000);
        super.renderFloatingItem(pGuiGraphics, pStack, pX, pY, pText);
        pose.popPose();
    }

    private void renderSlots(GuiGraphics g, int pMouseX, int pMouseY){
        var pose = g.pose();

        var item = menu.weaponSlot.getItem();
        if(!(item.getItem() instanceof BaseFirearm arm)) return;

        pose.pushPose();
        pose.translate(leftPos, topPos, 1000);

        for (BaseFirearm.Slot<?> slot : arm.getSlots()) {
            Item part = arm.getPart(item, slot);
            int x = slot.x() + menu.weaponCenterX;
            int y = slot.y() + menu.weaponCenterY;

            g.blit(BACKGROUND, x - 1, y - 1, 176, 0, 18, 18);
            g.renderItem(part != null ? new ItemStack(part) : ItemStack.EMPTY, x, y);
            if(isHovering(x, y, 16, 16, pMouseX, pMouseY)){
                renderSlotHighlight(g, x, y, -100, slotColor);
            }
        }
        g.flush();

        pose.popPose();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        var item = menu.weaponSlot.getItem();
        if(item.getItem() instanceof BaseFirearm arm){
            if(handleClickFirearmSlot(arm, pMouseX, pMouseY, pButton)) return true;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    private boolean handleClickFirearmSlot(BaseFirearm arm, double pMouseX, double pMouseY, int pButton) {
        for (BaseFirearm.Slot<?> slot : arm.getSlots()) {
            if(isHovering(slot.x() + menu.weaponCenterX, slot.y() + menu.weaponCenterY, 16, 16, pMouseX, pMouseY)){
                menu.clickWorkbenchSlot(slot);
                MWNetwork.CHANNEL.sendToServer(new PacketSetFirearmPart(slot.index()));
                return true;
            }
        }
        return false;
    }


    private Quaternionf rotation = new Quaternionf();
    private double rotationX = -20, rotationY = -70;

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        rotationX += -pDragY;
        rotationY += pDragX;

        if(rotationX > 20) rotationX = 20;
        if(rotationX < -20) rotationX = -20;
        rotationY %= 360;
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    private void renderWeapon(GuiGraphics g){
        rotation.set(0,0,0,1);

        rotation.rotateX((float) (rotationX * Mth.DEG_TO_RAD));
        rotation.rotateY((float) (rotationY * Mth.DEG_TO_RAD));

        var pose = g.pose();
        pose.pushPose();
        pose.translate(menu.weaponCenterX + leftPos, menu.weaponCenterY + topPos, 300);

        int scale = 8;
        pose.scale(16.0F * scale, 16.0F * scale, 16.0F * scale);
        pose.mulPose(rotation);
        var renderer = Minecraft.getInstance().getItemRenderer();
        var item = menu.slots.get(0).getItem();
        var buf = g.bufferSource();
        var model = renderer.getModel(item, null, null, 0);
        Lighting.setupFor3DItems();
        pose.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));

        renderer.render(item, ItemDisplayContext.GROUND, false, pose, buf, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, model);
        g.flush();

        pose.popPose();
    }
}
