package space.quinoaa.modularweapons.client.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import space.quinoaa.modularweapons.client.interaction.WeaponUseHandler;
import space.quinoaa.modularweapons.item.BaseFirearm;

public class WeaponOverlay implements IGuiOverlay {


    @Override
    public void render(ForgeGui gui, GuiGraphics g, float ptick, int screenWidth, int screenHeight) {
        var mc = gui.getMinecraft();
        var plr = mc.player;
        if(plr == null) return;
        ItemStack item = plr.getMainHandItem();
        if(!(item.getItem() instanceof BaseFirearm firearm)) return;

        var ammo = firearm.getAmmo(plr);
        Font font = gui.getFont();

        var pose = g.pose();
        float scale = 3;
        if(screenWidth < 600) scale = 2;
        pose.pushPose();
        pose.translate(screenWidth - 10, screenHeight - 10, 0);
        pose.scale(scale, scale, scale);

        int maxDura = 0, dura = 0;
        if(ammo != null){
            maxDura = ammo.getMaxDamage();
            dura = ammo.getMaxDamage() - ammo.getDamageValue();
        }
        int i = renderRight(dura + "/" + maxDura, g, font, 0, -font.lineHeight, 0xFFFFFF);
        if(ammo != null){
            pose.translate(i, -font.lineHeight, 0);
            float scaleItem = font.lineHeight / 16f;
            pose.scale(scaleItem, scaleItem, scaleItem);
            g.renderItem(ammo, -16, 0, 0);
        }

        pose.popPose();

        if(WeaponUseHandler.cooldown > 0){
            float t = WeaponUseHandler.cooldown - ptick;
            int centerX = screenWidth / 2, centerY = screenHeight / 2 + 10;
            int len = (int) (t * 5);
            g.fill(centerX - len, centerY, centerX + len, centerY + 2, 0xffffffff);
        }
    }

    private int renderRight(String text, GuiGraphics g, Font font, int x, int y, int color) {
        int off = x - font.width(text);
        g.drawString(font, text, off, y, color);
        return off;
    }
}
