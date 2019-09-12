package net.slotigork.mixin;

import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.slotigork.interfaces.Timeable;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.render.entity.RabbitEntityRenderer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitEntityRenderer.class)
public class RabbitEntityRendererMixin {
    private static final Identifier BROWN_SKIN_ANGRY = new Identifier("rabidrabbits:textures/entity/rabbit/brown.png");
    private static final Identifier WHITE_SKIN_ANGRY = new Identifier("rabidrabbits:textures/entity/rabbit/white.png");
    private static final Identifier BLACK_SKIN_ANGRY = new Identifier("rabidrabbits:textures/entity/rabbit/black.png");
    private static final Identifier GOLD_SKIN_ANGRY = new Identifier("rabidrabbits:textures/entity/rabbit/gold.png");
    private static final Identifier SALT_SKIN_ANGRY = new Identifier("rabidrabbits:textures/entity/rabbit/salt.png");
    private static final Identifier WHITE_SPOTTED_SKIN_ANGRY = new Identifier("rabidrabbits:textures/entity/rabbit/white_splotched.png");
    private static final Identifier TOAST_SKIN_ANGRY = new Identifier("rabidrabbits:textures/entity/rabbit/toast.png");
    private static final Identifier CAERBANNOG_SKIN = new Identifier("rabidrabbits:textures/entity/rabbit/caerbannog.png");

    @Inject(method = "method_4102", at = @At("HEAD"), cancellable = true)
    protected void ifNightThenAngrySkin(RabbitEntity rabbitEntity_1, CallbackInfoReturnable<Identifier> cir) {
        if (!((Timeable) rabbitEntity_1.getEntityWorld()).isDay()) {
            String string_1 = Formatting.strip(rabbitEntity_1.getName().getString());
            if (string_1 != null && "Toast".equals(string_1)) {
                cir.setReturnValue(TOAST_SKIN_ANGRY);
            } else {
                int rabbitType = rabbitEntity_1.getRabbitType();
                if (rabbitType == 1){
                    cir.setReturnValue(WHITE_SKIN_ANGRY);
                }
                else if (rabbitType == 2){
                    cir.setReturnValue(BLACK_SKIN_ANGRY);
                }
                else if (rabbitType == 3){
                    cir.setReturnValue(WHITE_SPOTTED_SKIN_ANGRY);
                }
                else if (rabbitType == 4){
                    cir.setReturnValue(GOLD_SKIN_ANGRY);
                }
                else if (rabbitType == 5){
                    cir.setReturnValue(SALT_SKIN_ANGRY);
                }
                else if (rabbitType == 99){
                    cir.setReturnValue(CAERBANNOG_SKIN);
                }
                else{
                    cir.setReturnValue(BROWN_SKIN_ANGRY);
                }
            }

        }
    }
}
