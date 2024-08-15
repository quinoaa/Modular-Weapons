package space.quinoaa.modularweapons.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.quinoaa.modularweapons.client.interaction.WeaponUseHandler;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(cancellable = true, method = "startAttack", at = @At("HEAD"))
    public void weaponAttack(CallbackInfoReturnable<Boolean> cir){
        if(WeaponUseHandler.attack()) cir.setReturnValue(true);
    }

    @Inject(cancellable = true, method = "continueAttack", at = @At("HEAD"))
    public void weaponAttackContinue(boolean pLeftClick, CallbackInfo ci){
        if(pLeftClick && WeaponUseHandler.attack()) ci.cancel();
    }

    @Inject(cancellable = true, method = "startUseItem", at = @At("HEAD"))
    public void weaponUse(CallbackInfo ci){
        if(WeaponUseHandler.use()) ci.cancel();
    }
}
