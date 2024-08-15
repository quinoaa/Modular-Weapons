package space.quinoaa.modularweapons.item.ammo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import space.quinoaa.modularweapons.item.BaseAmmo;

import java.lang.reflect.Type;

public class IncendiaryAmmo extends BaseAmmo {
    public IncendiaryAmmo(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onHitBlock(ServerPlayer user, BlockHitResult hit) {
        if(hit.getType() == HitResult.Type.MISS) return;
        var level = user.serverLevel();


        var pos = hit.getBlockPos().offset(hit.getDirection().getNormal());
        if(level.getBlockState(pos).canBeReplaced()) level.setBlock(pos, FireBlock.getState(level, pos), 3);
    }

    @Override
    public void onHitEntity(ServerPlayer user, Entity entity) {
        entity.setSecondsOnFire(4);
    }
}
