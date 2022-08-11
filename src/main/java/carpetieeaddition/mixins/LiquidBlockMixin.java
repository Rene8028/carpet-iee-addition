package carpetieeaddition.mixins;

import carpetieeaddition.IEECarpetSettings;
import carpetieeaddition.IEECarpetServer;
import carpetieeaddition.utils.MessageUtil;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.apache.logging.log4j.core.jmx.Server;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin {

    @Shadow
    protected abstract void fizz(LevelAccessor level, BlockPos pos);

    @Inject(
        method = "shouldSpreadLiquid",
        at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/material/FluidState;isSource()Z"),
        cancellable = true
    )
    private void generateDeepalate(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (IEECarpetSettings.DeepalateGeneration
            && level.getBlockState(pos.below()).is(Blocks.CRACKED_DEEPSLATE_BRICKS)
            && level.getFluidState(pos).isEmpty()) {
            level.setBlock(pos, Blocks.COBBLED_DEEPSLATE.defaultBlockState(),1);
            this.fizz(level, pos);
            cir.setReturnValue(false);
            if (IEECarpetSettings.DebugMod){
                MessageUtil.sendServerDebugLog("生成深板岩");
            }
        }
    }

}
