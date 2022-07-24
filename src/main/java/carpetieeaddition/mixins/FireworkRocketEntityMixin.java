package carpetieeaddition.mixins;

import carpetieeaddition.IEECarpetSettings;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {
    @Shadow
    private int lifeTime;
    private int i;
//    @Accessor
//    dataTracker
//    @Accessor
//    public abstract void setDeltaMovement(double p_20335_, double p_20336_, double p_20337_);
    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V", at = @At("RETURN"))
    private void NoRandomLifetime(World world, double x, double y, double z, ItemStack stack, CallbackInfo ci){
//        if (!stack.isEmpty() && stack.hasTag()) {
//            this.entityData.set(DATA_ID_FIREWORKS_ITEM, p_37034_.copy());
//            i += stack.getOrCreateSubTag("Fireworks").getByte("Flight");
//        }
        if (IEECarpetSettings.FireworkNoRandomLifetime) {
            this.lifeTime = 10 * i;
        }
        if (IEECarpetSettings.FireworkNoExplode){
            this.lifeTime = 99999;
        }
        if (IEECarpetSettings.FireworkNoRandomMovement){
//            this.setDeltaMovement(0, 0.05D, 0);
        }
    }
}
