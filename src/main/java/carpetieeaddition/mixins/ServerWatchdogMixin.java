package carpetieeaddition.mixins;

import carpetieeaddition.IEECarpetServer;
import carpetieeaddition.IEECarpetSettings;
import carpetieeaddition.utils.MessageUtil;


import com.google.common.collect.Streams;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.ServerWatchdog;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.IntegerValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerWatchdog.class)
public abstract class ServerWatchdogMixin implements Runnable {
    @Shadow
    protected abstract void exit();

    private long maxTickTime;
    private final DedicatedServer server;
    Logger WDLOGGER = IEECarpetServer.getLogger();

    public ServerWatchdogMixin(DedicatedServer dedicatedserver) {
        this.server = dedicatedserver;
        this.maxTickTime = server.getMaxTickLength();
    }

    @Override
    public void run(){
        while(this.server.isRunning()) {
            long l = server.getNextTickTime();
            long m = Util.getMillis();
            long n = m - l;
            if (n > this.maxTickTime) {
                if (IEECarpetSettings.DisableWatchdog){
                    WDLOGGER.fatal("A single server tick took {} seconds (should be max {})", String.format(Locale.ROOT, "%.2f", (float)n / 1000.0F), String.format(Locale.ROOT, "%.2f", 0.05F));
                    WDLOGGER.fatal("Although considering it to be crashed,but since IEECarpetSettings.DisableWatchdog is on, server will keep running.");
                    MessageUtil.sendServerinfoLog("阻止服务器看门狗崩溃成功！");
                }
                else {
                    WDLOGGER.fatal("A single server tick took {} seconds (should be max {})", String.format(Locale.ROOT, "%.2f", (float)n / 1000.0F), String.format(Locale.ROOT, "%.2f", 0.05F));
                    WDLOGGER.fatal("Considering it to be crashed, server will forcibly shutdown.");
                    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                    ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
                    StringBuilder stringBuilder = new StringBuilder();
                    Error error = new Error("Watchdog");
                    ThreadInfo[] var11 = threadInfos;
                    int var12 = threadInfos.length;

                    for(int var13 = 0; var13 < var12; ++var13) {
                        ThreadInfo threadInfo = var11[var13];
                        if (threadInfo.getThreadId() == this.server.getRunningThread().getId()) {
                            error.setStackTrace(threadInfo.getStackTrace());
                        }

                        stringBuilder.append(threadInfo);
                        stringBuilder.append("\n");
                    }

                    CrashReport crashReport = new CrashReport("Watching Server", error);
                    this.server.fillSystemReport(crashReport.getSystemReport());
                    CrashReportCategory crashReportCategory = crashReport.addCategory("Thread Dump");
                    crashReportCategory.setDetail("Threads", stringBuilder);
                    CrashReportCategory crashReportCategory2 = crashReport.addCategory("Performance stats");
                    crashReportCategory2.setDetail("Random tick rate", () -> {
                        return ((IntegerValue)this.server.getWorldData().getGameRules().getRule(GameRules.RULE_RANDOMTICKING)).toString();
                    });
                    crashReportCategory2.setDetail("Level stats", () -> {
                        return (String)Streams.stream(this.server.getAllLevels()).map((serverLevel) -> {
                            ResourceKey var10000 = serverLevel.dimension();
                            return var10000 + ": " + serverLevel.getWatchdogStats();
                        }).collect(Collectors.joining(",\n"));
                    });
                    Bootstrap.realStdoutPrintln("Crash report:\n" + crashReport.getFriendlyReport());
                    File var10002 = new File(this.server.getServerDirectory(), "crash-reports");
                    SimpleDateFormat var10003 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
                    Date var10004 = new Date();
                    File file = new File(var10002, "crash-" + var10003.format(var10004) + "-server.txt");
                    if (crashReport.saveToFile(file)) {
                        WDLOGGER.error("This crash report has been saved to: {}", file.getAbsolutePath());
                    } else {
                        WDLOGGER.error("We were unable to save this crash report to disk.");
                    }

                    this.exit();
                }
            }
            try {
                Thread.sleep(l + this.maxTickTime - m);
            } catch (InterruptedException var15) {
            }
        }
    }
}
