package carpetieeaddition;

import carpet.settings.Rule;
import org.spongepowered.asm.mixin.Debug;

import static carpet.settings.RuleCategory.*;

public class IEECarpetSettings {
    public static final String IEE = "IEE";
    public static final String TEST = "TEST";
    @Rule(desc = "IEECA-DEBUG", category = {CREATIVE, IEE, TEST})
    public static boolean DebugMod = false;

    @Rule(desc = "如果下面有裂纹深板岩砖，则会生成深板岩圆石而不是圆石", category = {SURVIVAL, IEE, FEATURE})
    public static boolean DeepalateGeneration = false;

    @Rule(
            desc = "海洋之心再生-守卫者被骷髅杀死有概率掉落海洋之心(自定义概率)",
            extra = "设置为0来禁用",
            options = {"0", "0.2", "1"},
            strict = false,
            category = {SURVIVAL, IEE, FEATURE}
    )
    public static double renewableHeart_of_the_sea = 0.0D;

    @Rule(desc = "禁用烟花的随机动量", category = {IEE, FEATURE})
    public static boolean FireworkNoRandomMovement = false;
    @Rule(desc = "禁用烟花的随机生命周期", category = {IEE, FEATURE})
    public static boolean FireworkNoRandomLifetime = false;
    @Rule(desc = "禁用烟花爆炸（无限生命周期）", category = {IEE, FEATURE})
    public static boolean FireworkNoExplode = false;
    @Rule(desc = "禁用ServerWatchdog看门狗", category = {IEE})
    public static boolean DisableWatchdog = false;
    @Rule(
            name = "commandFreecam",
            desc = "改进的freecame指令",
            category = {IEE, COMMAND},
            options = {"true", "false", "ops", "0", "1", "2", "3", "4"}
    )
    public static String commandFreecam = "true";
    //Enables /freecam command to toggle your camera mode.
    @Rule(
            desc = "海绵再生-在地狱停留一定时间的守卫者转变成远古守卫者(自定义时间)",
            extra = "设置为-1来禁用，单位：刻(tick)",
            options = {"-1", "30", "60","90"},
            strict = false,
            category = {SURVIVAL, IEE, FEATURE}
    )
    public static int renewableSponge = -1;
}
