package carpetieeaddition.mixins;

import carpetieeaddition.IEECarpetServer;
import carpetieeaddition.IEECarpetSettings;
import com.google.common.collect.Streams;
import net.minecraft.Bootstrap;
import net.minecraft.server.dedicated.DedicatedServerWatchdog;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;



@Mixin(DedicatedServerWatchdog.class)
public abstract class ServerWatchdogMixin implements Runnable {
    @Shadow
    protected abstract void shutdown();
    
    private long maxTickTime;
    
    public ServerWatchdogMixin(MinecraftDedicatedServer server) {
        this.maxTickTime = server.getMaxTickTime();
    }

    @Override
    public void run(){
        while(IEECarpetServer.minecraft_server.isRunning()) {
            long l = IEECarpetServer.minecraft_server.getTimeReference();
            long m = Util.getMeasuringTimeMs();
            long n = m - l;
            if (n > this.maxTickTime) {
                if (IEECarpetSettings.DisableWatchdog){
                    IEECarpetServer.LOGGER.fatal("A single server tick took {} seconds (should be max {})", String.format(Locale.ROOT, "%.2f", (float)n / 1000.0F), String.format(Locale.ROOT, "%.2f", 0.05F));
                    IEECarpetServer.LOGGER.fatal("Although considering it to be crashed,but since IEECarpetSettings.DisableWatchdog is on, server will keep running.");
                }
                else {
                    IEECarpetServer.LOGGER.fatal("A single server tick took {} seconds (should be max {})", String.format(Locale.ROOT, "%.2f", (float)n / 1000.0F), String.format(Locale.ROOT, "%.2f", 0.05F));
                    IEECarpetServer.LOGGER.fatal("Considering it to be crashed, server will forcibly shutdown.");
                    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                    ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
                    StringBuilder stringBuilder = new StringBuilder();
                    Error error = new Error("Watchdog");
                    ThreadInfo[] var11 = threadInfos;
                    int var12 = threadInfos.length;

                    for(int var13 = 0; var13 < var12; ++var13) {
                        ThreadInfo threadInfo = var11[var13];
                        if (threadInfo.getThreadId() == IEECarpetServer.minecraft_server.getThread().getId()) {
                            error.setStackTrace(threadInfo.getStackTrace());
                        }

                        stringBuilder.append(threadInfo);
                        stringBuilder.append("\n");
                    }

                    CrashReport crashReport = new CrashReport("Watching Server", error);
                    IEECarpetServer.minecraft_server.addSystemDetails(crashReport.getSystemDetailsSection());
                    CrashReportSection crashReportSection = crashReport.addElement("Thread Dump");
                    crashReportSection.add("Threads", stringBuilder);
                    CrashReportSection crashReportSection2 = crashReport.addElement("Performance stats");
                    crashReportSection2.add("Random tick rate", () -> {
                        return ((GameRules.IntRule)IEECarpetServer.minecraft_server.getSaveProperties().getGameRules().get(GameRules.RANDOM_TICK_SPEED)).toString();
                    });
                    crashReportSection2.add("Level stats", () -> {
                        return (String)Streams.stream(IEECarpetServer.minecraft_server.getWorlds()).map((serverWorld) -> {
                            RegistryKey var10000 = serverWorld.getRegistryKey();
                            return var10000 + ": " + serverWorld.getDebugString();
                        }).collect(Collectors.joining(",\n"));
                    });
                    Bootstrap.println("Crash report:\n" + crashReport.asString());
                    File var10002 = new File(IEECarpetServer.minecraft_server.getRunDirectory(), "crash-reports");
                    SimpleDateFormat var10003 = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
                    Date var10004 = new Date();
                    File file = new File(var10002, "crash-" + var10003.format(var10004) + "-server.txt");
                    if (crashReport.writeToFile(file)) {
                        IEECarpetServer.LOGGER.error("This crash report has been saved to: {}", file.getAbsolutePath());
                    } else {
                        IEECarpetServer.LOGGER.error("We were unable to save this crash report to disk.");
                    }

                    this.shutdown();
                }
            }
            try {
                Thread.sleep(l + this.maxTickTime - m);
            } catch (InterruptedException var15) {
            }
        }
    }
}
