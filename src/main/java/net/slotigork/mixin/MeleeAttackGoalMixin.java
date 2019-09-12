package net.slotigork.mixin;

import net.slotigork.interfaces.Rabidable;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.passive.RabbitEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin extends Goal {

    @Shadow
    MobEntityWithAi mob;

    @Shadow
    public void stop(){}

    /*Since goals are non-removable, we inject code at canStart() and shouldContinue() to prohibit these methods from
     running when the rabbit is NOT rabid*/
    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    protected void dontStartIfRabbitNotRabid(CallbackInfoReturnable<Boolean> cir) {

        if (this.mob instanceof RabbitEntity) {
            if (!((Rabidable) this.mob).isRabid()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "shouldContinue", at = @At("HEAD"), cancellable = true)
    protected void dontContinueIfRabbitNotRabid(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof RabbitEntity) {
            if (!((Rabidable) this.mob).isRabid()) {
                stop();
                cir.setReturnValue(false);
            }
        }
    }
}
