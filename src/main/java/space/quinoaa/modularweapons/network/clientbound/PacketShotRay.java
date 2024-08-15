package space.quinoaa.modularweapons.network.clientbound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DustColorTransitionParticle;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class PacketShotRay {
    public final Vec3 start, end;

    public PacketShotRay(Vec3 start, Vec3 end) {
        this.start = start;
        this.end = end;
    }

    public void encode(FriendlyByteBuf b) {
        b.writeDouble(start.x);
        b.writeDouble(start.y);
        b.writeDouble(start.z);
        b.writeDouble(end.x);
        b.writeDouble(end.y);
        b.writeDouble(end.z);
    }

    public static PacketShotRay decode(FriendlyByteBuf b) {
        return new PacketShotRay(
                new Vec3(b.readDouble(), b.readDouble(), b.readDouble()),
                new Vec3(b.readDouble(), b.readDouble(), b.readDouble())
        );
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        if(!ctx.getDirection().getReceptionSide().isClient()) return;
        ctx.setPacketHandled(true);
        ctx.enqueueWork(new Runnable() {
            @Override
            public void run() {
                ClientLevel level = Minecraft.getInstance().level;
                if(level == null) return;

                var step = end.subtract(start).normalize().scale(0.3);
                var dist = end.subtract(start).length();
                Vec3 pos = start;
                var options = new DustParticleOptions(new Vector3f(1, 1, 1), 0.2f);
                for (double i = 0; i < dist; i += .3) {
                    pos = pos.add(step);
                    Minecraft.getInstance().particleEngine.createParticle(options, pos.x, pos.y, pos.z, 0, 0, 0);
                }
            }
        });
    }
}
