package carpetieeaddition.mixins;

import carpetieeaddition.IEECarpetSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(GuardianEntity.class)
abstract class GuardianEntityMixin extends HostileEntity
{
	public GuardianEntityMixin(EntityType<? extends GuardianEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer)
	{
		super.dropLoot(source, causedByPlayer);
		if (IEECarpetSettings.renewableHeart_of_the_sea > 0.0D)
		{
			if (source.getAttacker() instanceof SkeletonEntity && this.random.nextDouble() < IEECarpetSettings.renewableHeart_of_the_sea)
			{
				this.dropStack(new ItemStack(Items.HEART_OF_THE_SEA));
			}
		}
	}
}
