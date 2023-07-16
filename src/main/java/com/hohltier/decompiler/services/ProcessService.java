package com.hohltier.decompiler.services;

import com.hohltier.decompiler.Main;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import java.lang.management.ManagementFactory;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.Preferences;

@UtilityClass
public class ProcessService {

    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    public static final Preferences PREFERENCES = Preferences.userNodeForPackage(Main.class);

    @SneakyThrows
    public static void start() {
        Optional<String> java = ProcessHandle.current().info().command();
        if (java.isPresent()) {
            String classPath = ManagementFactory.getRuntimeMXBean().getClassPath();
            String main = Main.class.getCanonicalName();
            new ProcessBuilder(java.get(), "-cp", classPath, main).start();
        }
    }

}