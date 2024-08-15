package space.quinoaa.modularweapons.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.BlockHitResult;

public class BaseAmmo extends Item {

    public BaseAmmo(Properties pProperties) {
        super(pProperties);
    }

    public void onHitBlock(ServerPlayer user, BlockHitResult hit){

    }

    public void onHitEntity(ServerPlayer user, Entity entity){

    }


}
