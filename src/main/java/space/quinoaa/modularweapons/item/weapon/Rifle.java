package space.quinoaa.modularweapons.item.weapon;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import space.quinoaa.modularweapons.init.MWNetwork;
import space.quinoaa.modularweapons.item.BaseAmmo;
import space.quinoaa.modularweapons.item.BaseFirearm;
import space.quinoaa.modularweapons.item.part.BarrelPart;
import space.quinoaa.modularweapons.item.part.ScopePart;
import space.quinoaa.modularweapons.network.clientbound.PacketShotRay;

import java.util.function.IntConsumer;

public class Rifle extends BaseFirearm {
    Slot<ScopePart> scope;
    Slot<BarrelPart> barrel;

    public Rifle(Properties pProperties) {
        super(pProperties);
    }


    @Override
    protected void initSlots() {
        scope = createSlot(-20, -20, ScopePart.class, new Vec3(0, 3.3/16.0, 0));
        barrel = createSlot(20, 0, BarrelPart.class, new Vec3(0, 1.5/16.0, -8/16.0));
    }

    @Override
    public void attack(Player plr, ItemStack stack, IntConsumer cooldown) {
        var barrel = getPart(stack, this.barrel);
        if(barrel == null) return;

        var ammo = getAmmo(plr);
        if(ammo == null) return;

        var level = plr.level();

        plr.playSound(SoundEvents.STONE_BREAK);
        cooldown.accept(barrel.cooldown);

        if(level.isClientSide) return;
        ServerLevel serverLevel = (ServerLevel) level;
        ServerPlayer srvPlayer = (ServerPlayer) plr;
        ammo.hurtAndBreak(1, plr, (a)->{});
        BaseAmmo ammoItem = (BaseAmmo) ammo.getItem();

        var start = plr.getEyePosition();
        var max = start.add(plr.getLookAngle().scale(barrel.range));
        ClipContext ctx = new ClipContext(start, max, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, plr);
        var hit = serverLevel.clip(ctx);
        var end = hit.getType() == HitResult.Type.BLOCK ? hit.getLocation() : max;

        ammoItem.onHitBlock(srvPlayer, hit);

        var entityBox = new AABB(start, max).expandTowards(1, 1, 1);
        for (Entity entity : serverLevel.getEntities(plr, entityBox)) {
            if(entity.getBoundingBox().clip(start, end).isEmpty()) continue;
            entity.hurt(plr.damageSources().playerAttack(plr), barrel.damage);
            ammoItem.onHitEntity(srvPlayer, entity);
        }

        var players = PacketDistributor.NEAR.with(()->new PacketDistributor.TargetPoint(start.x, start.y, start.z, 200, level.dimension()));

        MWNetwork.CHANNEL.send(players, new PacketShotRay(start, end));
    }

    @Override
    public float getZoom(ItemStack stack) {
        var part = getPart(stack, scope);

        if(part == null) return 1;

        return part.zoom;
    }


}
