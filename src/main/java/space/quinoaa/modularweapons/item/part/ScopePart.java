package space.quinoaa.modularweapons.item.part;

import net.minecraft.resources.ResourceLocation;
import space.quinoaa.modularweapons.item.BaseFirearmPart;

public class ScopePart extends BaseFirearmPart {
    public final float zoom;

    public ScopePart(Properties pProperties, float zoom) {
        super(pProperties);
        this.zoom = zoom;
    }
}
