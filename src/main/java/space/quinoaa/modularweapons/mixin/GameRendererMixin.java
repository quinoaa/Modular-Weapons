package space.quinoaa.modularweapons.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.quinoaa.modularweapons.client.interaction.WeaponUseHandler;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "getFov", cancellable = true, at = @At("RETURN"))
    public void zoom(Camera pActiveRenderInfo, float pPartialTicks, boolean pUseFOVSetting, CallbackInfoReturnable<Double> cir){
        if(!WeaponUseHandler.isZooming) return;
        var zoom = WeaponUseHandler.getZoom();
        if(zoom == 1) return;

        cir.setReturnValue(cir.getReturnValue() / zoom);
    }
}
