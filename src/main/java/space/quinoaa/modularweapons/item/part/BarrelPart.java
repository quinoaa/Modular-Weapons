package space.quinoaa.modularweapons.item.part;

import net.minecraft.resources.ResourceLocation;
import space.quinoaa.modularweapons.item.BaseFirearmPart;

public class BarrelPart extends BaseFirearmPart {
    public final int cooldown;
    public final float damage;
    public final double range;

    public BarrelPart(Properties pProperties, int cooldown, float damage, double range) {
        super(pProperties);
        this.cooldown = cooldown;
        this.damage = damage;
        this.range = range;
    }
}
