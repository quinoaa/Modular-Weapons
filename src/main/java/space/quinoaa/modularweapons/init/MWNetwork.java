package space.quinoaa.modularweapons.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import space.quinoaa.modularweapons.ModularWeapons;
import space.quinoaa.modularweapons.network.clientbound.PacketShotRay;
import space.quinoaa.modularweapons.network.serverbound.PacketFire;
import space.quinoaa.modularweapons.network.serverbound.PacketSetFirearmPart;

public class MWNetwork {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ModularWeapons.MODID, "main"),
            ()->PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        CHANNEL.registerMessage(0, PacketSetFirearmPart.class, PacketSetFirearmPart::encode, PacketSetFirearmPart::decode, PacketSetFirearmPart::handle);
        CHANNEL.registerMessage(1, PacketFire.class, PacketFire::encode, PacketFire::decode, PacketFire::handle);
        CHANNEL.registerMessage(2, PacketShotRay.class, PacketShotRay::encode, PacketShotRay::decode, PacketShotRay::handle);
    }
}
