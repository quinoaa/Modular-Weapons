package space.quinoaa.modularweapons.mixin;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.quinoaa.modularweapons.client.interaction.WeaponUseHandler;

@Mixin(Options.class)
public class OptionsMixin {

    @Inject(method = "sensitivity", cancellable = true, at = @At("RETURN"))
    public void zoom(CallbackInfoReturnable<OptionInstance<Double>> cir){
        if(!WeaponUseHandler.isZooming) return;

        var ret = cir.getReturnValue();

        cir.setReturnValue(new OptionInstance<>("options.sensitivity", OptionInstance.noTooltip(), (c, v)-> Component.empty(), OptionInstance.UnitDouble.INSTANCE, ret.get() / WeaponUseHandler.getZoom(), t->{}));
    }
}
