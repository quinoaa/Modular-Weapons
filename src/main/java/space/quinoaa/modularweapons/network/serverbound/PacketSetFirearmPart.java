package space.quinoaa.modularweapons.network.serverbound;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import space.quinoaa.modularweapons.menu.WeaponWorkbenchMenu;

import java.util.function.Supplier;

public class PacketSetFirearmPart {
    public final int index;

    public PacketSetFirearmPart(int index) {
        this.index = index;
    }


    public void encode(FriendlyByteBuf b) {
        b.writeInt(index);
    }

    public static PacketSetFirearmPart decode(FriendlyByteBuf b) {
        return new PacketSetFirearmPart(b.readInt());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        if(!ctx.getDirection().getReceptionSide().isServer()) return;
        ctx.setPacketHandled(true);

        var sender = ctx.getSender();
        ctx.enqueueWork(()->{
            if(sender.containerMenu instanceof WeaponWorkbenchMenu menu){
                var slot = menu.getWeaponSlot(index);
                if(slot == null) return;
                menu.clickWorkbenchSlot(slot);
            }
        });
    }
}
