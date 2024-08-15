package space.quinoaa.modularweapons.network.serverbound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import space.quinoaa.modularweapons.item.BaseFirearm;

import java.util.function.Supplier;

public class PacketFire {
    public final float xRot, yRot;

    public PacketFire(float xRot, float yRot) {
        this.xRot = xRot;
        this.yRot = yRot;
    }


    public void encode(FriendlyByteBuf b) {
        b.writeFloat(xRot);
        b.writeFloat(yRot);
    }

    public static PacketFire decode(FriendlyByteBuf b) {
        return new PacketFire(b.readFloat(), b.readFloat());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        if(!ctx.getDirection().getReceptionSide().isServer()) return;

        var plr = ctx.getSender();
        if(plr == null) return;

        ctx.setPacketHandled(true);
        ctx.enqueueWork(()->{
            ItemStack stack = plr.getMainHandItem();
            if(!(stack.getItem() instanceof BaseFirearm arm)) return;

            arm.attack(plr, stack, t->{});
        });
    }
}
