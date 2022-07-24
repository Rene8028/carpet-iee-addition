package carpetieeaddition;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.*;

public class IEECarpetSettings {
    public static final String IEE = "IEE";

    @Rule(desc = "如果下面有裂纹深板岩砖，则会生成深板岩圆石而不是圆石", category = {SURVIVAL, IEE, FEATURE})
    public static boolean DeepalateGeneration = false;

    @Rule(
            desc = "守卫者被骷髅杀死有概率掉落海洋之心",
            extra = "Set it to 0 to disable",
            options = {"0", "0.2", "1"},
            strict = false,
            category = {IEE, FEATURE}
    )
    public static double renewableHeart_of_the_sea = 0.0D;

    @Rule(desc = "禁用烟花的随机动量", category = {IEE, FEATURE})
    public static boolean FireworkNoRandomMovement = false;
    @Rule(desc = "禁用烟花的随机生命周期", category = {IEE, FEATURE})
    public static boolean FireworkNoRandomLifetime = false;
    @Rule(desc = "禁用烟花爆炸（无限生命周期）", category = {IEE, FEATURE})
    public static boolean FireworkNoExplode = false;
}
