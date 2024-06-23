package cn.ksmcbrigade.olo.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.awt.*;

@Mixin(SplashOverlay.class)
public class LoadingOverlayMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"),cancellable = true)
    public void renderAfter(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci){
        this.client.setOverlay(null);
        this.client.setScreenAndRender(new TitleScreen(false));
        ci.cancel();
    }


    @Inject(method = "render",at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceReload;getProgress()F"),locals = LocalCapture.CAPTURE_FAILSOFT)
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci, int i, int j, long l, float f, float g, float h, int k, int p, double d, int q, double e, int r, int s){
        Window window = MinecraftClient.getInstance().getWindow();
        context.fill(0,0,window.getScaledWidth(),window.getScaledHeight(),Color.WHITE.getRGB());
        context.drawTexture(new Identifier("olo","textures/gui/title/mojang.png"), window.getScaledWidth()/2-256/2, window.getScaledHeight()/2-48/2, 0,0, 256,48,256,48);
    }

    @Inject(method = "renderProgressBar",at = @At("HEAD"),cancellable = true)
    public void progress(DrawContext drawContext, int minX, int minY, int maxX, int maxY, float opacity, CallbackInfo ci){
        ci.cancel();
    }
}
