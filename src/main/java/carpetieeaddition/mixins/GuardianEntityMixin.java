package carpetieeaddition.mixins;

import carpetieeaddition.IEECarpetSettings;
import carpetieeaddition.utils.MessageUtil;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(Guardian.class)
abstract class GuardianEntityMixin extends Monster
{
	@Shadow public abstract MobType getMobType();

	private int timeInNether =0;

	public GuardianEntityMixin(EntityType<? extends Guardian> entityType, Level level)
	{
		super(entityType, level);
	}

	@Override
	protected void dropFromLootTable(DamageSource source, boolean causedByPlayer)
	{
		super.dropFromLootTable(source, causedByPlayer);
		if (IEECarpetSettings.renewableHeart_of_the_sea > 0.0D)
		{
			if (source.getEntity() instanceof Skeleton && this.random.nextDouble() < IEECarpetSettings.renewableHeart_of_the_sea)
			{
				this.spawnAtLocation(new ItemStack(Items.HEART_OF_THE_SEA));
				if (IEECarpetSettings.DebugMod){
					MessageUtil.sendServerDebugLog("生成海洋之心");
				}
			}
		}
	}
	@Inject(method = "Lnet/minecraft/world/entity/monster/Guardian;aiStep()V", at = @At("HEAD"))
	private void aiStepinjected(CallbackInfo ci) {
		if (IEECarpetSettings.renewableSponge >= 0 && this.level.dimensionType().piglinSafe() && this.getType() == EntityType.GUARDIAN) {
			++this.timeInNether;
			if (this.timeInNether >= IEECarpetSettings.renewableSponge) {
				this.convertTo(EntityType.ELDER_GUARDIAN, false);
				this.timeInNether = 0;

				if(IEECarpetSettings.DebugMod){
					MessageUtil.sendServerDebugLog("守卫者转换远古守卫者");
				}
			}
		} else {
			this.timeInNether = 0;
		}
	}

}
