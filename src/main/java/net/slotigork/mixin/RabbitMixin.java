package net.slotigork.mixin;

import net.slotigork.interfaces.Timeable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.slotigork.interfaces.Rabidable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RabbitEntity.class)
public abstract class RabbitMixin extends AnimalEntity implements Rabidable {
    private boolean isRabid;

    public RabbitMixin(EntityType<? extends RabbitEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
        isRabid = false;
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        this.goalSelector.add(4, new RabbitMixin.RabbitAttackGoal( this));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new FollowTargetGoal(this, WolfEntity.class, true));
    }

    @Inject(method = "mobTick", at = @At("HEAD"))
    private void checkIfTurning(CallbackInfo ci){

        if(!this.isRabid && !(((Timeable)(World) this.world).isDay())){
            this.isRabid = true;
        }
        else if(this.isRabid && (((Timeable)(World) this.world).isDay())){
            this.isRabid =  false;
        }
    }

    @Override
    public boolean isRabid() {
        return isRabid;
    }

/*    public boolean isDay(){
        //TODO: Flytta till en egen mixin
        int time = (int) world.getTimeOfDay();
        return (time >= 0 && time < 13000);
    }*/

    static class RabbitAttackGoal extends MeleeAttackGoal {
        public RabbitAttackGoal(RabbitMixin rabbitEntity_1) {
            super(rabbitEntity_1, 1.4D, true);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity livingEntity_1) {
            return (double)(4.0F + livingEntity_1.getWidth());
        }
    }


}